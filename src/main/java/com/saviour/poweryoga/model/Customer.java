package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
    @NamedQuery(name = "Customer.MyAdvisor", query = "from Customer c WHERE c.myAdvisor.userId = :fid")
})
public class Customer extends Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseOrder> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private List<CreditCard> creditCards = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Faculty myAdvisor;

    public List<PurchaseOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PurchaseOrder> orders) {
        this.orders = orders;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void addCreditCard(CreditCard card) {
        creditCards.add(card);
    }

    public Faculty getMyAdvisor() {
        return myAdvisor;
    }

    public void setMyAdvisor(Faculty myAdvisor) {
        this.myAdvisor = myAdvisor;
    }

}
