/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.Service;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import testjob.OperationEnum;
import util.Config;
import static util.DAO.getSession;



/**
 *
 * @author spok
 */
public class ServiceImpl extends AbstractObjectDB<Service>
{
    public final static String TABLE_NAME = "Service";
    public ServiceImpl() {
        super(TABLE_NAME);
    }
    private static ServiceImpl instance = null;
    
    public static ServiceImpl getInstance() {
        if (instance == null) {
            instance = new ServiceImpl();
        }
        return instance;
    }

    /**
     * show service 
     */
    public void getAllServiceQuery(){
        List<Service> list   = new LinkedList<>();
        Session session = getSession();
	try {
            list = session.createQuery("from " + TABLE_NAME).list();   
            System.out.println("count service: " + list.size());
            for(Service s: list){
               
                System.out.println("service:" + s.getServiceName()  + " with " + s.getOperation() +
                        " param " + s.getParametr());
            }          
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }              
    }
    /**
     * get service by name
     * @param nameService
     * @return
     * @throws SQLException 
     */
    public Service getServiceByName(String nameService) throws SQLException {
        Service object = null;
        try {
            Query categoryQuery = getSession().createQuery(
                    " from " + TABLE_NAME + " where SERVICE_NAME = :param1").setString("param1", nameService);
            object = (Service) categoryQuery.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return object;
    }
    
    /**
     * add client
     * @param operation
     * @param serviceName
     * @param value 
     */
    public  void addClient(String operation, String serviceName,float value ){
        Service  service =  new Service();
        service.setOperation(operation);
        service.setServiceName(serviceName);
        service.setParametr(value);
        try {
            if(ServiceImpl.getInstance().insert(service)){
                System.out.println("Defined service " + serviceName +" with " + operation + " value " + value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * show services
     * @param commandSplit 
     */
    public void showServices(String[] commandSplit) {
         if(commandSplit.length > Config.COUNT_PARAM_SHOW_SERVICES){
                     System.err.println(Config.COMMAND_HAS_NO_PARAMETR);    
                     System.out.println(Config.SHOW_SERVISES_EXAMPLE);
        }
         else{
             getAllServiceQuery();
         }
    }
    
    /***
     * add service
     * @param str[]
     * @throws SQLException 
     */
    public void addService(String[] str) throws SQLException{
        
        if(!Config.validateForCountParametr(str, 1)){
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_SERVICE);   
                   System.out.println(Config.ADD_SERVICE_EXAMPLE);
        }      
        else if(!Config.validateParam(str[2].trim())){
            System.err.println("bad parametr operation");
        }
        else if(str[2].trim().equals(OperationEnum.average.toString())){
            float averageValue  = 0;
            try{
                for(int i = Config.START_INDEX_PARAM_COM_ADD_SERVICE; i< str.length;i++){
                    if(str[i].length() != 0){
                        averageValue  = averageValue + Float.valueOf(str[i].trim());
                    }
                }
                averageValue = averageValue/(str.length - 
                        Config.START_INDEX_PARAM_COM_ADD_SERVICE);
                ServiceImpl.getInstance().addClient(str[2].trim(), str[1].trim(), averageValue);
            }
            catch(Exception e){System.err.println(e.getMessage());}          
        }
        else{
            if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_SERVICE)){
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_SERVICE);   
                   System.err.println(Config.ADD_SERVICE_EXAMPLE);
            }
            else            
            {
                addClient(str[2].trim(), str[1].trim(), Float.valueOf(str[3]));
            }
        }   
    }
}
