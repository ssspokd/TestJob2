

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import logger.Loggers;
import testjob.TestJob;
import javax.interceptor.Interceptors;

/**
 *
 * @author ssspokd
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    @Interceptors(Loggers.class)
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        TestJob t =  new TestJob();
        t.run();
    }
    
}
