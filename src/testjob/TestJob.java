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
import DAO.ServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
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
               ServiceImpl.getInstance().showServices(commandSplit);
            }
            //show clients
            else if(strCommand.equals(CommandEnum.clients.toString())){
               ClientImpl.getInstance().showClients(commandSplit);
            }
            //add services
            else if(strCommand.equals(CommandEnum.service.toString())){
                ServiceImpl.getInstance().addService(commandSplit);                          
            }
            //add client
            else if(strCommand.equals(CommandEnum.client.toString())){
               ClientImpl.getInstance().addClient(commandSplit);             
            }
            //add_client_service
            else if(strCommand.equals(CommandEnum.add_client_service.toString())){
               ClientServiceImpl.getInstance().addServiceForClient(commandSplit);             
            }
            //consumption 
            else if(strCommand.equals(CommandEnum.consumption.toString()))
            {
                ConsumptionImpl.getInstance().addConsumption(commandSplit);
            }          
            //bill for client
            else if(strCommand.equals(CommandEnum.bill.toString()))
            {
                BillImpl.getInstance().addBillClient(commandSplit);
            }
            //show bill client
            else if(strCommand.equals(CommandEnum.show_bill.toString())){
                BillImpl.getInstance().showBillForClient(commandSplit);
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
}
