/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.controller;

import com.saviour.poweryoga.model.Product;
import com.saviour.poweryoga.serviceI.IProductService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bidur
 * @version 0.0.1
 */
@Named("productController")
@SessionScoped
public class ProductController implements Serializable {

    @Autowired
    private IProductService ProductService;

    private Product product;
    private List<Product> listOfProducts;

    private String errorMsg = null;

    private String successMsg = null;

    public ProductController() {
        //product = new Product();
    }

    /**
     * Save Product data
     *
     * @return
     */
    public String saveProduct() {
        product.setStatus(Product.statusType.ACTIVE);
        ProductService.saveProduct(product);
        successMsg = "Product is created successfully";
        return ("/views/admin/manageProduct.xhtml?faces-redirect=true");
    }

    /**
     * Display update Product data page
     *
     * @return
     */
    public String updateProduct() {
        ProductService.updateProduct(product);
        successMsg = "Product is updated successfully";
        return ("/views/admin/manageProduct.xhtml?faces-redirect=true");
    }

    /**
     * Update Product data
     *
     * @param Id
     * @return
     */
    public String editProduct(Long Id) {
        product = ProductService.getProductById(Id);
        return "editProduct";
    }

    /**
     * display add Product form
     *
     * @return
     */
    public String addProduct() {
        product = new Product();
        return ("/views/admin/addProduct.xhtml?faces-redirect=true");
    }

    /**
     * Delete Product entry
     *
     * @param id
     * @return
     */
    public String deleteProduct(long id) {
        try {
            product = ProductService.getProductById(id);

            //Set its status inactive
            product.setStatus(Product.statusType.INACTIVE);
            ProductService.updateProduct(product);
            successMsg = "Product is deleted successfully";
        } catch (Exception ex) {
            errorMsg = "Delete Product Failed";
        }
        return ("/views/admin/manageProduct.xhtml?faces-redirect=true");
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getListOfProducts() {
        listOfProducts = ProductService.getAllProducts();
        return listOfProducts;
    }

    public void setListOfProducts(List<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

}
