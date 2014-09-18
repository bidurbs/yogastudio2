package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * This class is for Product Order and contains Product Order features.
 *
 * @author TalakB
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.findOrderByCustomerId", query = "from PurchaseOrder p where p.customer.userId=:customerId")
})
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar buyingDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ShoppingCart shoppingCart;

    //@OneToMany(mappedBy = "orders")
    @ManyToOne
    private Customer customer;

    @Transient
    private String buyDateStr;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Calendar buyingDate, ShoppingCart shoppingCart, Customer customer) {
        this.buyingDate = buyingDate;
        this.shoppingCart = shoppingCart;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(Calendar buyingDate) {
        this.buyingDate = buyingDate;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getBuyDateStr() {
        return buyDateStr;
    }

    public void setBuyDateStr(String buyDateStr) {
        this.buyDateStr = buyDateStr;
    }

}
