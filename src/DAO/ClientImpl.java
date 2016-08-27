/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.Client;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.Config;
import static util.DAO.getSession;

/**
 *
 * @author spok
 */
public class ClientImpl extends AbstractObjectDB<Client>
{
    public final static String TABLE_NAME = "Client";
    public ClientImpl() {
        super(TABLE_NAME);
    }
    private static ClientImpl instance = null;
    
    public static ClientImpl getInstance() {
        if (instance == null) {
            instance = new ClientImpl();
        }
        return instance;
    }
   
    /**
     * show client, query
     */
    public void getClientQuery(){
        List list = null;
        Session session = getSession();    
	try {
            session.getTransaction().begin();
            list = session.createSQLQuery("select  c.client_name,s.service_name from "
            + "(client c join client_service cs on c.id = cs.id_client) join\n" +
            "  service s on cs.id_service = s.id\n" +
            " group by c.client_name,s.service_name").list();   
            System.out.println("count client: " + list.size());
            Config.printFormListIterator(list);
            session.getTransaction().commit();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }              
    }
    
    /**
     * get object Client from name client
     * @param nameClient
     * @return
     * @throws SQLException 
     */
    public Client getClienttByName(String nameClient) throws SQLException {
        Client object = new Client();
        try {
            Query categoryQuery = getSession().createQuery(
                    " from " + TABLE_NAME + " where CLIENT_NAME = :param1").setString("param1", nameClient);
            object = (Client) categoryQuery.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return object;
    }
    
    /**
     * show all clients
     * @param str 
     */
    public void showClients(String[] str){
        if(str.length  > Config.COUNT_PARAM_SHOW_CLIENT){
            System.err.println(Config.COMMAND_HAS_NO_PARAMETR);              
        }
        else{
            getClientQuery();
        }
    }
    
    /***
     * add client
     * @param name Name client
     * @throws SQLException 
     */
    public void addClient(String[] str) throws SQLException{
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_CLIENT))
        {
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_CLIENT);    
                   System.out.println(Config.ADD_CLIENT_EXAMPLE);                  
        }       
        else{
                String nameClient = str[1];
                Client client = new Client();
                client.setClientName(nameClient);
                insert(client);
                System.out.println("Defined client " + str[1]);  
        }         
    }
}
