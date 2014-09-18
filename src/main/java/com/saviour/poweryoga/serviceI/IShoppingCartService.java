/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.ShoppingCart;
import com.saviour.poweryoga.model.ShoppingCartItem;

/**
 *
 * @author shahin
 */
public interface IShoppingCartService {

    ShoppingCart addToCart(ShoppingCart cart);

    ShoppingCart removeFromCart(ShoppingCart cart, ShoppingCartItem item);
    
}
