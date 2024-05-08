package com.microservices.serviceproduct.repository;

import com.microservices.serviceproduct.entities.Category;
import com.microservices.serviceproduct.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    List<Product> findByCategory(Category category);
}
