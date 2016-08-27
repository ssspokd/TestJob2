package DAO.Interfaces;


import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.DAO;

public  abstract class AbstractObjectDB<T> extends DAO implements ObjectDB<T>
{

    public static final int INTERVAL = 1;
    private final String tableName;

    public AbstractObjectDB(String tableName) {
        this.tableName = tableName;
    }

   


    @Override
    public void updateObject(T t) throws SQLException {
        Session session = null;
	try {
            session = getSession();
            org.hibernate.Transaction tx  = beginTransaction();
            tx.begin();
            session.update(t);
            session.getTransaction().commit();	    
            } 
        catch (Exception e) {
            System.err.println(e.getMessage());
        }     
    }

    @Override
    public void updateOrInsertObject(T t) throws SQLException {
        Session session = null;
        try {
            session = getSession();
            org.hibernate.Transaction tx  = beginTransaction();
            tx.begin();
            session.saveOrUpdate(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }       
    }

    @Override
    public void deleteObject(T t) throws SQLException {
        Session session = null;
        try {
            session = getSession();
            org.hibernate.Transaction tx  = beginTransaction();
            tx.begin();
            session.delete(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }     
    }
    
    @Override
    public boolean insert(T t) throws SQLException {
        Session session = getSession();
        boolean ret = false;
	try {         
                  
            beginTransaction().begin();
            getSession().save(t);
            beginTransaction().commit();        
            ret = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            beginTransaction().rollback();
        }
        finally{
            close();
        }
      return ret;
    }

   

    @Override
    public T getObjectByID(long id) throws SQLException {
        T object = null;
        Session session = null;
        try {
            session = getSession();
            Query categoryQuery = getSession().createQuery(
                    " from " + tableName + " where ID = :id").setLong("id", id);          
            object = (T) categoryQuery.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return object;
    }

    @Override
    public ArrayList<T> getAllObjects() throws SQLException {
        //throw  new UnsupportedTypeException();
        List<T> list   = null;
        Session session = null;
	try {
            session = getSession();         
            list = session.createQuery("from " + tableName).list();         		    
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }       
        return (ArrayList<T>) list;     
    }
    
}
