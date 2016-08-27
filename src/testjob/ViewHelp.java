/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjob;

import util.Config;

/**
 *
 * @author ssspokd
 */
public class ViewHelp {
    
    public  static  final void helpView( String[] str){
        
        if(str.length == 1){
            Config.printCommand();
        }
        else if(str.length != 2 ){
            Config.printErrorBadCountParam(Config.COUNT_PARAM_HELP);
            System.out.println(Config.SHOW_HELP_EXAMPLE);
        }else{
            String strCommand = str[1];
            if(strCommand.equals(CommandEnum.add_client_service.toString())){
                System.out.println(Config.COUNT_PARAM_COMMAND_ADD_CLIENT_SERVICE);
            }
            else if(strCommand.equals(CommandEnum.bill.toString())){
                 System.out.println(Config.BILL_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.client.toString())){
                 System.out.println(Config.ADD_CLIENT_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.clients.toString())){
                 System.out.println(Config.SHOW_CLIENT_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.consumption.toString())){
                 System.out.println(Config.COUNT_PARAM_COMMAND_ADD_CONSUMPTION);
            }
            else if(strCommand.equals(CommandEnum.service.toString())){
                 System.out.println(Config.ADD_SERVICE_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.services.toString())){
                 System.out.println(Config.SHOW_SERVISES_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.show_bill.toString())){
                 System.out.println(Config.SHOW_BILL_CLIENT_EXAMPLE);
            }
            else if(strCommand.equals(CommandEnum.quit.toString())){
                 System.out.println(Config.SHOW_EXIT_EXAMPLE);
            }
            else{
                System.err.println("command "  + strCommand +" not finde");
            }
            
        }
    }
}
