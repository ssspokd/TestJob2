/*/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

//import javax.interceptor.InvocationContext;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author spok
 */
public class Loggers {
    
    public Object showSQL(InvocationContext ictx) throws Exception
    {
        //включение логирования SQL запросов
        Logger sqlLogger = Logger.getLogger("org.hibernate.SQL");
	sqlLogger.setLevel(Level.DEBUG);
	//Включение логирования параметров запросов
	Logger descLogger = Logger.getLogger("org.hibernate.type.descriptor.sql.BasicBinder");
	descLogger.setLevel(Level.TRACE);
	//Выполнение аннотированного метода сервиса
	Object res = ictx.proceed();
	//Выключение логирования SQL
	sqlLogger = Logger.getLogger("org.hibernate.SQL");
	sqlLogger.setLevel(Level.INFO);
	//Выключение логирования параметров
	descLogger = Logger.getLogger("org.hibernate.type.descriptor.sql.BasicBinder");
	descLogger.setLevel(Level.INFO);
	return res;
    }
}
