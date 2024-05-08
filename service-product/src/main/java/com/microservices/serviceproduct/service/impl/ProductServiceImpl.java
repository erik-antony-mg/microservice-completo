package com.microservices.serviceproduct.service.impl;

import com.microservices.serviceproduct.entities.Category;
import com.microservices.serviceproduct.entities.Product;
import com.microservices.serviceproduct.repository.CategoryRepository;
import com.microservices.serviceproduct.repository.ProductRepository;
import com.microservices.serviceproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }


    @Override
    public Product getOneProduct(String productId) {
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException("no existe el producto"));
    }

    @Override
    public Product createProduct(Product product) {
        String categoryName= product.getCategory().getName().toLowerCase();

        Category category=product.getCategory();
        category.setName(categoryName);

        product.setCreate_at(LocalDate.now());
        product.setStatus("CREATED");
        if (categoryRepository.findByName(category.getName()).isEmpty()){
            categoryRepository.save(category);
        }
        else {

            Category categoryBd =categoryRepository.findByName(category.getName()).get();
            product.setCategory(categoryBd);
        }
        return productRepository.save(product);
    }

    @Override
    public Product editProduct(Product product, String productId) {
        Product productDb= productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("no se encontro un producto"));
        Category categoryDb= categoryRepository.findByName(product.getCategory().getName().toLowerCase())
                .orElseThrow(()-> new RuntimeException("no se encontro una categoria"));

        productDb.setName(product.getName());
        productDb.setStock(product.getStock());
        productDb.setDescription(product.getDescription());
        productDb.setCategory(categoryDb);

        return productRepository.save(productDb);
    }

    @Override
    public Product deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("no se encontro el producto"));
        product.setStatus("DELETE");
        return  productRepository.save(product);
    }

    @Override
    public Product updateStock(String productId, double quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("no se encontro el producto"));
        product.setStock(quantity);

        return productRepository.save(product);
    }

    @Override
    public void deleteProductTest(String productId) {
        productRepository.deleteById(productId);
    }


}
