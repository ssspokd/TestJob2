/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.Client;
import DAO.Entity.ClientService;
import DAO.Entity.Service;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import testjob.TestJob;
import util.Config;
import static util.DAO.getSession;

/**
 *
 * @author spok
 */
public class ClientServiceImpl extends AbstractObjectDB<ClientService>
{
    public final static String TABLE_NAME = "ClientService";
    public ClientServiceImpl() {
        super(TABLE_NAME);
    }
    private static ClientServiceImpl instance = null;
    
    public static ClientServiceImpl getInstance() {
        if (instance == null) {
            instance = new ClientServiceImpl();
        }
        return instance;
    }
    
    /***
     * 
     * @param idClient
     * @param idService
     * @return
     * @throws SQLException 
     */
    public ClientService getClientService(int idClient,int idService) throws SQLException {
        ClientService object = null;
        try {
            Query categoryQuery = getSession().createQuery(
                    " from " + TABLE_NAME + " where iD_CLIENT = :param1 and ID_SERVICE = :param2").
                    setInteger("param1", idClient).setInteger("param2", idService);
            object = (ClientService) categoryQuery.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return object;
    }
    
     /***
     * add servcie for client
     * @param str 
     */
    public void addServiceForClient(String[] str){
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_CLIENT_SERVICE)){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_CLIENT);       
            System.out.println(Config.ADD_CLIENT_SERVICE_EXAMPLE);         
        }       
        else{
            try {
                Client client =   ClientImpl.getInstance().getClienttByName(str[1]);
                Service service = ServiceImpl.getInstance().getServiceByName(str[2]);
                if(client == null || service == null){
                    System.err.println("Client or service not finde");                  
                }
                else if((getClientService(client.getId(), service.getId())) != null){
                    System.err.println("This service is added to the client");
                }
                else{
                    ClientService cs =  new ClientService();
                    cs.setService(service);
                    cs.setClient(client);
                    insert(cs);                    
                    System.out.println("Added service " + service.getServiceName() +
                    " to " + client.getClientName());
                }               
            } catch (SQLException ex) {
                Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
