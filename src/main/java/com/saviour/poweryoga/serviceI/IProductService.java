package com.saviour.poweryoga.serviceI;

import com.saviour.poweryoga.model.Product;
import java.util.List;

/**
 *
 * @author bidur
 */
public interface IProductService {

    public void saveProduct(Product product);

    public List<Product> getAllProducts();

    public void updateProduct(Product product);

    public Product getProductById(Long Id);

    public void deleteProduct(Product product);

    public List<Product> searchProduct(String name);

    public List<Product> getFeaturedProducts();

}
