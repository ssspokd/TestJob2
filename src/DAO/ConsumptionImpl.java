/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Entity.Consumption;
import DAO.Interfaces.AbstractObjectDB;



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

}
