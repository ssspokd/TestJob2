/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.BillClient;
import DAO.Interfaces.AbstractObjectDB;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import testjob.OperationEnum;
import static util.DAO.getSession;
import org.hibernate.Session;



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
}
