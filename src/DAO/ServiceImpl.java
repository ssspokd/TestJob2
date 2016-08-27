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

    
    public void printScreen(){
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
    public Service getObjectByName(String nameService) throws SQLException {
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
    
    public  void insertClient(String operation, String serviceName,float value ){
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
}
