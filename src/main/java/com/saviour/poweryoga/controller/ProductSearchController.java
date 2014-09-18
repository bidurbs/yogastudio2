/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Product;
import com.saviour.poweryoga.serviceI.IProductService;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
@Named(value = "productSearchController")
@SessionScoped
public class ProductSearchController implements Serializable {

    @Autowired
    private IProductService productService;

    private Product product;
    private List<Product> products;

    private String productName;
    private int noOfAvailableProducts;

    public ProductSearchController() {

    }

    /**
     * Search Product based on the name entered by customer
     *
     * @return Product page
     */
    public String searchProduct() {
        products = productService.searchProduct(productName);
        noOfAvailableProducts = products.size();
        productName = null;
        return ("/views/customer/product.xhtml?faces-redirect=true");
    }

    /**
     * Display all product in the system
     *
     * @return Product page
     */
    public String displayProducts() {
        products = productService.getAllProducts();
        noOfAvailableProducts = products.size();
        return ("/views/customer/product.xhtml?faces-redirect=true");
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNoOfAvailableProducts() {
        return noOfAvailableProducts;
    }

    public void setNoOfAvailableProducts(int noOfAvailableProducts) {
        this.noOfAvailableProducts = noOfAvailableProducts;
    }

    /**
     * View detail information of a product from Product Page
     *
     * @param product Selected Product
     * @return Product Detail Page
     */
    public String viewProductDetail(Product product) {
        this.product = product;
        return ("/views/customer/productDetail.xhtml?faces-redirect=true");
    }

}
