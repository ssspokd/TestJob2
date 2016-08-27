package util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Transaction;

/**
 *
 * @author seostella.com
 */
public class DAO {
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal session = new ThreadLocal();
    private static  org.hibernate.Transaction tx  = null;
    private static final SessionFactory sessionFactory =
            new AnnotationConfiguration().configure().buildSessionFactory();

    protected DAO() {
    }

    public static Session getSession() {
        Session session = (Session) DAO.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            DAO.session.set(session);
        }
        if(!session.isOpen()){
            session = sessionFactory.openSession();
            DAO.session.set(session);
        }
        return session;
    }

    protected void begin() {
        getSession().getTransaction();
    }

     protected Transaction beginTransaction() {
        if(tx== null){
            tx =   getSession().getTransaction();      
        }     
        return tx;
    }
    
    protected void commit() {
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        DAO.session.set(null);
    }

    public static void close() {
        getSession().close();
        DAO.tx = null;
        DAO.session.set(null);
    }
}
