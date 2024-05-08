package com.microservices.serviceproduct;

import com.microservices.serviceproduct.entities.Category;
import com.microservices.serviceproduct.entities.Product;
import com.microservices.serviceproduct.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ServiceProductApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_ReturnLisProduct(){
        Product product=Product.builder()
                .name("balon")
                .category(Category.builder().categoryId("120").name("futbol").build())
                .description("sirve para jugar futbol")
                .stock(10.00)
                .price(20.0)
                .status("CREATED")
                .create_at(LocalDate.now())
                .build();
        productRepository.save(product);

        List<Product> productList= productRepository.findByCategory(product.getCategory());
        Assertions.assertEquals(productList.size(),1);
    }
}
