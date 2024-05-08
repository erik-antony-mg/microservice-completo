package com.microservices.serviceproduct.repository;

import com.microservices.serviceproduct.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {

    Optional<Category> findByName(String categoryName);
}
