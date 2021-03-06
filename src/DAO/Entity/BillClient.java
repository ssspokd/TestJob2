package DAO.Entity;
// Generated 26.08.2016 15:13:22 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BillClient generated by hbm2java
 */
@Entity
@Table(name="BILL_CLIENT"
)
public class BillClient  implements java.io.Serializable {


     private int id;
     private Client client;
     private float billAmount;
     private Date dateBill;

    public BillClient() {
    }

    public BillClient(int id, Client client, float billAmount, Date dateBill) {
       this.id = id;
       this.client = client;
       this.billAmount = billAmount;
       this.dateBill = dateBill;
    }
   
     @Id 

    
    @Column(name="ID", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_CLIENT", nullable=false)
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    
    @Column(name="BILL_AMOUNT", nullable=false)
    public float getBillAmount() {
        return this.billAmount;
    }
    
    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_BILL", nullable=false, length=19)
    public Date getDateBill() {
        return this.dateBill;
    }
    
    public void setDateBill(Date dateBill) {
        this.dateBill = dateBill;
    }




}


