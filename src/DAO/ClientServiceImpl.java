/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.ClientService;
import DAO.Interfaces.AbstractObjectDB;
import java.sql.SQLException;
import org.hibernate.Query;
import static util.DAO.getSession;

/**
 *
 * @author spok
 */
public class ClientServiceImpl extends AbstractObjectDB<ClientService>
{
    public final static String TABLE_NAME = "ClientService";
    public ClientServiceImpl() {
        super(TABLE_NAME);
    }
    private static ClientServiceImpl instance = null;
    
    public static ClientServiceImpl getInstance() {
        if (instance == null) {
            instance = new ClientServiceImpl();
        }
        return instance;
    }
    public ClientService getObject(int idClient,int idService) throws SQLException {
        ClientService object = null;
        try {
            Query categoryQuery = getSession().createQuery(
                    " from " + TABLE_NAME + " where iD_CLIENT = :param1 and ID_SERVICE = :param2").
                    setInteger("param1", idClient).setInteger("param2", idService);
            object = (ClientService) categoryQuery.uniqueResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return object;
    }
}
