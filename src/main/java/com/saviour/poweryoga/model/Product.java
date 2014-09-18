/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * This class is for Product and contains Product features.
 *
 * @author TalakB
 * @version 0.0.1
 */
@Entity
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(name = "Product.searchProduct", query = "FROM Product p WHERE p.status='ACTIVE' and p.name LIKE :pname"),
    @NamedQuery(name = "Product.getAllFeatured", query = "FROM Product p order by rand()"),
    @NamedQuery(name = "Product.findAllProducts", query = "FROM Product p WHERE p.status=:status"),
    @NamedQuery(name = "Product.findAllActiveProducts", query = "FROM Product p WHERE p.status=:status")

})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String imageURL;

    private String description;

    private int quantity;

    public enum statusType {

        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    private statusType status;

    public Product() {

    }

    public Product(String name, double price) {

        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }

}
