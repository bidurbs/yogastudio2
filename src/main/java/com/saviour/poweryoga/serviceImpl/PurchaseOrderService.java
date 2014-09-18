package com.saviour.poweryoga.serviceImpl;

import com.saviour.poweryoga.crudfacade.CRUDFacadeImpl;
import com.saviour.poweryoga.model.Customer;
import com.saviour.poweryoga.model.PurchaseOrder;
import com.saviour.poweryoga.model.ShoppingCart;
import com.saviour.poweryoga.serviceI.IPurchaseOrderService;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Md Mojahidul Islam
 * @version 0.0.1
 */
@Transactional
@Service
public class PurchaseOrderService implements IPurchaseOrderService {

    @Autowired
    private CRUDFacadeImpl crudfacade;

    /**
     * This method will save Purchase Order
     *
     * @param shoppingCart
     * @param customer
     * @param buyDate
     * @return Saved Purchase Order
     */
    @Override
    public PurchaseOrder savePurchaseOrder(ShoppingCart shoppingCart, Customer customer, Calendar buyDate) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(buyDate, shoppingCart, customer);
        return (PurchaseOrder) crudfacade.save(purchaseOrder);

    }

    /**
     * This method will find list of order for a customer
     *
     * @param customerId
     * @return Purchase Order list
     */
    @Override
    public List<PurchaseOrder> findOrderByCustomerId(Long customerId) {
        Map<String, Long> paramaters = new HashMap<>(1);
        paramaters.put("customerId", customerId);

        List<PurchaseOrder> orders = crudfacade.findWithNamedQuery("PurchaseOrder.findOrderByCustomerId", paramaters);
        return orders;
    }

}
