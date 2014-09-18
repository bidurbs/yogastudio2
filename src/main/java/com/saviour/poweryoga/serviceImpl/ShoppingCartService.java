package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.ShoppingCart;
import com.saviour.poweryoga.model.ShoppingCartItem;
import com.saviour.poweryoga.serviceI.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Md Mojahidul Islam
 * @author TalakB
 * @version 0.0.1
 */
@Transactional
@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    /**
     * This method will add item in the shopping cart
     *
     * @param cart
     * @return Saved shopping cart
     */
    @Override
    public ShoppingCart addToCart(ShoppingCart cart) {
        return (ShoppingCart) crudfacade.merge(cart);
    }

    /**
     * This method will remove item from shopping cart
     *
     * @param cart
     * @param item
     * @return Shopping cart after removal
     */
    @Override
    public ShoppingCart removeFromCart(ShoppingCart cart, ShoppingCartItem item) {
        cart.getShoppingCartItems().remove(item);
        ShoppingCartItem it = (ShoppingCartItem) crudfacade.read(item.getId(), ShoppingCartItem.class);
        crudfacade.delete(it);
        return cart;
    }
}
