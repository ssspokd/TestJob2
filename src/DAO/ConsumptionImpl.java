/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.Client;
import DAO.Entity.ClientService;
import DAO.Entity.Consumption;
import DAO.Entity.Service;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import testjob.OperationEnum;
import testjob.TestJob;
import util.Config;



/**
 *
 * @author spok
 */
public class ConsumptionImpl extends AbstractObjectDB<Consumption>
{
    public final static String TABLE_NAME = "Consumption";
    public ConsumptionImpl() {
        super(TABLE_NAME);
    }
    private static ConsumptionImpl instance = null;
    
    public static ConsumptionImpl getInstance() {
        if (instance == null) {
            instance = new ConsumptionImpl();
        }
        return instance;
    }
    
    /***
     * 
     * @param str 
     */
    public void addConsumption(String[] str){
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_CONSUMPTION)){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_CONSUMPTION);       
            System.out.println(Config.ADD_CONSUMPTION_EXAMPLE);
            
        }       
        else{
            try {
                Client client = ClientImpl.getInstance().getObjectByName(str[1]);
                Service service = ServiceImpl.getInstance().getObjectByName(str[2]);
                if(client == null || service == null){
                    System.err.println("Client or service not finde");
                    return;
                }
                if(service.getOperation().equals(OperationEnum.constant.toString())){
                    System.err.println("for the operation of the service is set as constantan, "
                            + "when calculating the value will not be considered");
                }
                ClientService clientService =  ClientServiceImpl.getInstance()
                        .getObject(client.getId(), service.getId());
                if(clientService == null){
                    System.err.println("not find service for this is client");
                }  
                else{
                       Consumption  consumption =  new Consumption();
                       consumption.setValuesConsumption(Integer.valueOf(str[3]));
                       consumption.setClientService(clientService);
                       ConsumptionImpl.getInstance().insert(consumption);
                       System.out.println("Added consumption " + Integer.valueOf(str[3])+ "  by service " +
                               service.getServiceName() + " to " + client.getClientName());
                }              
            } catch (SQLException ex) {
                Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
    }
}
