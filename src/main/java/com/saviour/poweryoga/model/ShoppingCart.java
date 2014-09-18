/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * This class is for Shopping Cart and contains Shopping Cart features.
 * 
 * @author TalakB
 * version 1.0.0
 */
@Entity
@Table(name="SHOPPINGCART")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private Users user;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar shopDate;

    private double totalPrice;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY,mappedBy ="shoppingCart" )
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Users user, Calendar shopDate, double totalPrice) {
        this.user = user;
        this.shopDate = shopDate;
        this.totalPrice = totalPrice;
    }

    public void addItem(ShoppingCartItem item) {
        shoppingCartItems.add(item);
    }

    public Calendar getShopDate() {
        return shopDate;
    }

    public void setShopDate(Calendar shopDate) {
        this.shopDate = shopDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
