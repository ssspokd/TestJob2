package util;

import java.util.List;
import java.util.ListIterator;
import testjob.CommandEnum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ssspokd
 */
public class Config {
    //error
    public static final String COMMAND_HAS_NO_PARAMETR =  "This command has no parameters"; 
    
    //count param
    public  static final int COUNT_PARAM_COMMAND_ADD_CLIENT = 2;
    public  static final int COUNT_PARAM_COMMAND_ADD_SERVICE = 4;
    public  static final int COUNT_PARAM_COMMAND_ADD_CONSUMPTION = 4;
    public  static final int COUNT_PARAM_COMMAND_ADD_CLIENT_SERVICE = 3;
    public  static final int COUNT_PARAM_BILL_CLIENT = 2;
    public  static final int COUNT_PARAM_HELP = 2;
    public  static final int COUNT_PARAM_SHOW_CLIENT = 1;
    public  static final int COUNT_PARAM_SHOW_SERVICES = 1;
    
    
    
    //str
    public  static final String ADD_CLIENT_SERVICE_EXAMPLE = "add_client_service <client_name> "
            + "<service_name>\n" +
            "       client_name – name client\n" +
            "       service_name - name serivce";
    
     public  static final String ADD_CONSUMPTION_EXAMPLE = "consumption <client_name> <service_name> "
             + "<value> - job calculated value for the service\n" +
        "	client_name – name client\n" +
        "	service_name – name service\n" +
        "       value - value";
    
     public static final String BILL_EXAMPLE = "bill <client_name> - count charge of client";
    
     public static final String ADD_CLIENT_EXAMPLE = "add_client_service ivan water\n" +
        "Added service water to ivan";
     
     public static final String ADD_SERVICE_EXAMPLE = ">service water multiply 5.5\n" +
        "Defined service water with multiply factor 5.5\n" +
        ">service tax constant 400\n" +
        "Defined service tax with constant value 400"+
         ">service tax average  400 100 100\n" +
        "Defined service tax with constant value 200";
     
     public static final String SHOW_SERVISES_EXAMPLE =">services\n" +
            "service water with multiply factor 5.5\n" +
            "service tax with constant value 400";
     public static final String SHOW_HELP_EXAMPLE = "help <command> \n" +
             "help clients";
     
     public static final String SHOW_CLIENT_EXAMPLE = ">clients\n" +
            "client ivan services water\n" +
            "client ivan services tax";
     
     public static final String SHOW_BILL_CLIENT_EXAMPLE = "show_bill <name_client>";
    
     public static final String SHOW_EXIT_EXAMPLE = "close programm";
     
    //print errot
    public static final void printErrorBadCountParam(int count) {
        System.err.println("Command must have " + count + " parameters");
    }
    
    /***
     * show all command
     */
    public static final void printCommand(){
        StringBuilder buffer =  new StringBuilder();
        System.out.println("enter the following commands");
        for(CommandEnum t: CommandEnum.values()){
            buffer.append(" ").append(t.toString());
        }
        System.out.println(buffer.toString());
    }
    
    
   
     /***
     * 
     * @param str
     * @param countParam
     * @return 
     */
    public static boolean validateForCountParametr(String[] str, int countParam){
        return (str.length != countParam);
    }
    
    /***
     * 
     * @param list 
     */
    public static void printFormListIterator(List list){
        for(ListIterator iter = list.listIterator(); iter.hasNext(); ) 
            {                   
                Object[] row = (Object[]) iter.next();
            for (Object row1 : row) {
                System.out.print(row1 + " ");
            } 
                System.out.println("");
            }
    }
    
    
}
