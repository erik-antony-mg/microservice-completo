package com.microservices.serviceproduct.service;

import com.microservices.serviceproduct.entities.Category;
import com.microservices.serviceproduct.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    List<Product> getAllProducts();
    List<Product> getAllProductsByCategory(Category category);
    Product getOneProduct(String productId);
    Product createProduct(Product product);
    Product editProduct(Product product,String productId);
    Product deleteProduct(String productId);
    Product updateStock(String productId,double quantity);
    void deleteProductTest(String productId);
}
