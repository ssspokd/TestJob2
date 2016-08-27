/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjob;


import DAO.BillImpl;
import DAO.ClientImpl;
import DAO.ClientServiceImpl;
import DAO.ConsumptionImpl;
import DAO.Entity.BillClient;
import DAO.Entity.Client;
import DAO.Entity.ClientService;
import DAO.Entity.Consumption;
import DAO.Entity.Service;
import DAO.ServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Config;

/**
 *
 * @author ssspokd
 */
public class TestJob {

    public TestJob() {
    }
    

    public void run() throws SQLException {
        String[] commandSplit;
        BufferedReader bReader = new BufferedReader (new InputStreamReader(System.in));  
        boolean run=  true;    
        try {
            while (run) {            
            {
            System.out.println("Enter comand (help)");
            commandSplit = bReader.readLine().split(" ");       
            //show services
            String strCommand = commandSplit[0];
            if(strCommand.equals(CommandEnum.services.toString())){
                showServices(commandSplit);
            }
            //show clients
            else if(strCommand.equals(CommandEnum.clients.toString())){
               showClients(commandSplit);
            }
            //add services
            else if(strCommand.equals(CommandEnum.service.toString())){
                addService(commandSplit);                          
            }
            //add client
            else if(strCommand.equals(CommandEnum.client.toString())){
               addClient(commandSplit);             
            }
            //add_client_service
            else if(strCommand.equals(CommandEnum.add_client_service.toString())){
               addServiceForClient(commandSplit);             
            }
            //consumption 
            else if(strCommand.equals(CommandEnum.consumption.toString()))
            {
                addConsumption(commandSplit);
            }          
            //bill for client
            else if(strCommand.equals(CommandEnum.bill.toString()))
            {
                billClient(commandSplit);
            }
            //show bill client
            else if(strCommand.equals(CommandEnum.show_bill.toString())){
                showBill(commandSplit);
            }
            //show help
            else if(strCommand.equals(CommandEnum.help.toString())){
                ViewHelp.helpView(commandSplit);
            }
            //close programm
            else if(strCommand.equals(CommandEnum.quit.toString())){
                System.exit(0);
            }
            else{
                Config.printCommand();
            }
        }
            }
        } catch (IOException ex) {
            Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            ClientImpl.getInstance().printScreen();
        }
    }
    
    /***
     * 
     * @param str
     * @throws SQLException 
     */
    private void showBill(String[] str) throws SQLException{
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_BILL_CLIENT)){
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_BILL_CLIENT);                   
        }       
        else{
            Client client =   ClientImpl.getInstance().getObjectByName(str[1]);
            if(client == null){
                System.err.println("Client  not finde");
                return;
            }
            List list = BillImpl.getInstance().showAllBillForClient(client.getId());        
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
     * validate param
     * @param param
     * @return 
     */
    private boolean validateParam(String param){
        return(param.equals(OperationEnum.average.toString()) ||
                param.equals(OperationEnum.constant.toString()) ||
                param.equals(OperationEnum.multiply.toString()));
    }
    
    /***
     * add client
     * @param name Name client
     * @throws SQLException 
     */
    private void addClient(String[] str) throws SQLException{
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_CLIENT))
        {
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_CLIENT);    
                   System.out.println(Config.ADD_CLIENT_EXAMPLE);
                   
        }       
        else{
                String nameClient = str[1];
                Client client = new Client();
                client.setClientName(nameClient);
                ClientImpl.getInstance().insert(client);
                System.out.println("Defined client " + str[1]);  
        }         
    }
    
    /***
     * add service
     * @param str[]
     * @throws SQLException 
     */
    private void addService(String[] str) throws SQLException{
        
        if(!Config.validateForCountParametr(str, 1)){
                   Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_SERVICE);   
                   System.out.println(Config.ADD_SERVICE_EXAMPLE);
        }      
        else if(!validateParam(str[2].trim())){
            System.err.println("bad parametr operation");
        }
        else if(str[2].trim().equals(OperationEnum.average.toString())){
            float averageValue  = 0;
            try{
                for(int i = 3; i< str.length;i++){
                    if(str[i].length() != 0){
                        averageValue  = averageValue + Float.valueOf(str[i].trim());
                    }
                }
                averageValue = averageValue/3;
                ServiceImpl.getInstance().insertClient(str[2].trim(), str[1].trim(), averageValue);
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
                ServiceImpl.getInstance().insertClient(str[2].trim(), str[1].trim(), Float.valueOf(str[3]));
            }
        }   
    }
    
    /***
     * add servcie for client
     * @param str 
     */
    private void addServiceForClient(String[] str){
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_COMMAND_ADD_CLIENT_SERVICE)){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_COMMAND_ADD_CLIENT);       
            System.out.println(Config.ADD_CLIENT_SERVICE_EXAMPLE);         
        }       
        else{
            try {
                Client client =   ClientImpl.getInstance().getObjectByName(str[1]);
                Service service = ServiceImpl.getInstance().getObjectByName(str[2]);
                if(client == null || service == null){
                    System.err.println("Client or service not finde");
                    
                }
                else if((ClientServiceImpl.getInstance().getObject(client.getId(), service.getId())) != null){
                    System.err.println("This service is added to the client");
                }
                else{
                    ClientService cs =  new ClientService();
                    cs.setService(service);
                    cs.setClient(client);
                    ClientServiceImpl.getInstance().insert(cs);
                    
                    System.out.println("Added service " + service.getServiceName() +
                    " to " + client.getClientName());
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(TestJob.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    /***
     * 
     * @param str 
     */
    private void addConsumption(String[] str){
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
    
    /***
     * 
     * @param str 
     */
    private void billClient(String[] str){
        if(Config.validateForCountParametr(str, Config.COUNT_PARAM_BILL_CLIENT)){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_BILL_CLIENT);       
            System.out.println(Config.BILL_EXAMPLE);
            
        }  
        else{
            try {
                Client client = ClientImpl.getInstance().getObjectByName(str[1]);
                if(client == null){
                    System.err.println("Client not find");
                }
                else{
                    float ammount = BillImpl.getInstance().calculatedBill(client.getId());
                    BillImpl.getInstance().calculatedBill(client.getId());
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

    /**
     * show services
     * @param commandSplit 
     */
    private void showServices(String[] commandSplit) {
         if(commandSplit.length > Config.COUNT_PARAM_SHOW_SERVICES){
                     System.err.println(Config.COMMAND_HAS_NO_PARAMETR);    
                     System.out.println(Config.SHOW_SERVISES_EXAMPLE);
        }
         else{
                 ServiceImpl.getInstance().printScreen();
         }
    }
    
}
