/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.BillClient;
import DAO.Entity.Client;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import testjob.OperationEnum;
import static util.DAO.getSession;
import org.hibernate.Session;
import testjob.TestJob;
import util.Config;



/**
 *
 * @author spok
 */
public class BillImpl extends AbstractObjectDB<BillClient>
{
    public final static String TABLE_NAME = "Bill_Client";
    public BillImpl() {
        super(TABLE_NAME);
    }
    private static BillImpl instance = null;
    
    public static BillImpl getInstance() {
        if (instance == null) {
            instance = new BillImpl();
        }
        return instance;
    }

    /**
     * show bill for client, query
     * @param idClient
     * @return 
     */
    public List showAllBillForClient(int idClient){
        List list;
        list = new LinkedList<>();
        Session session = getSession();    
	try {
             list = session.createSQLQuery("select c.client_name, b.bill_amount, b.date_"
                    + "bill from  bill_client b\n" +
                    "join client c on b.id_client = c.id "
                    + "where c.id = :param").setInteger("param", idClient).list();             
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }       
        return list;
    }
 
    
    /**
     * calculated bill
     * @param idClient
     * @return 
     */
    public float calculatedBill(int idClient){
        float result = 0;
        Session session = getSession();
        List res = new ArrayList();
        try {
            res = getSession().createSQLQuery("select c.values_consumption, s.operation, "
                    + "s.parametr from\n" +
                    "(consumption c join client_service cs\n" +
                    "on c.id_client_service = cs.id)\n" +
                    "join service s on cs.id_service = s.id\n" +
                    "where cs.id_client = "  + idClient).list();           
            for(Object obj : res){               
                Object[] d = (Object[]) obj;
                if(d[1].equals(OperationEnum.multiply.toString())){
                    result = result +  (Float.valueOf(d[0].toString()) * 
                            Float.valueOf(d[2].toString()));
                }
                else if(d[1].equals(OperationEnum.constant.toString())){
                    result = result +   Float.valueOf(d[2].toString());
                }
                else if(d[1].equals(OperationEnum.average.toString())){
                    result = Float.valueOf(d[0].toString()) + 
                            Float.valueOf(d[2].toString());
                }
            }            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    /***
     * 
     * @param str
     * @throws SQLException 
     */
    public void showBill(String[] str) throws SQLException{
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_BILL_CLIENT)){
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_BILL_CLIENT);                   
        }       
        else{
            Client client =   ClientImpl.getInstance().getClienttByName(str[1]);
            if(client == null){
                System.err.println("Client  not finde");
                return;
            }
            List list = showAllBillForClient(client.getId());        
            if(list.isEmpty()){
                System.err.println("not bill for client " + client.getClientName());
            }
            else{
                System.out.println("count bill: " + list.size());  
                Config.printFormListIterator(list);
            }
            
        }     
    }
    
     /***
     * 
     * @param str 
     */
    public void addBillClient(String[] str){
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_BILL_CLIENT)){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_BILL_CLIENT);       
            System.out.println(Config.BILL_EXAMPLE);            
        }  
        else{
            try {
                Client client = ClientImpl.getInstance().getClienttByName(str[1]);
                if(client == null){
                    System.err.println("Client not find");
                }
                else{
                    float ammount = BillImpl.getInstance().calculatedBill(client.getId());
                    calculatedBill(client.getId());
                    BillClient  billClient =  new BillClient();
                    billClient.setClient(client);
                    billClient.setDateBill(new Date());
                    billClient.setBillAmount(ammount);
                    System.out.println("Amount for " + client.getClientName() + " " + ammount);
                    BillImpl.getInstance().insert(billClient);                 
                }
            } catch (SQLException ex) {
                Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
            } 
                
        }
    }
}
