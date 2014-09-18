/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.PurchaseOrder;
import com.saviour.poweryoga.model.ShoppingCart;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author shahin
 */
public interface IPurchaseOrderService {

    public PurchaseOrder savePurchaseOrder(ShoppingCart shoppingCart, Customer customer, Calendar buyDate);

    public List<PurchaseOrder> findOrderByCustomerId(Long customerId);
}
