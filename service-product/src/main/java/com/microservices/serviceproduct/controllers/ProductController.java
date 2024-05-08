package com.microservices.serviceproduct.controllers;

import com.microservices.serviceproduct.entities.Category;
import com.microservices.serviceproduct.entities.Product;
import com.microservices.serviceproduct.repository.CategoryRepository;
import com.microservices.serviceproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/list")
    ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false,defaultValue ="") String nameCategory){
        String categoryName;
        if (nameCategory.isEmpty()){
            if (productService.getAllProducts().isEmpty()){
                return ResponseEntity.noContent().build();
            }
            else {
                return ResponseEntity.ok(productService.getAllProducts());
            }
        }else {
            categoryName =nameCategory.toLowerCase();
            if (categoryRepository.findByName(categoryName).isEmpty()){
                return ResponseEntity.notFound().build();
            }
            else {
                Category category=categoryRepository.findByName(categoryName).get();
                return ResponseEntity.ok(productService.getAllProductsByCategory(category));
            }
        }
    }

    @GetMapping("/{productId}")
    ResponseEntity<Product> getOneProduct(@PathVariable String productId){
        return ResponseEntity.ok(productService.getOneProduct(productId));
    }

    @PostMapping("/create")
    ResponseEntity<Product> createdProduct(@Validated @RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{productId}")
    ResponseEntity<Product> deleteProduct(@PathVariable String productId){
      return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/edit/{productId}")
    ResponseEntity<Product> editPrduct(@PathVariable String productId,@Validated @RequestBody Product product){
        return ResponseEntity.ok(productService.editProduct(product,productId));
    }
    @PutMapping("/update/stock/{productId}")
    ResponseEntity<Product> editStock(@PathVariable String productId,@RequestParam Double stock){
        return ResponseEntity.ok(productService.updateStock(productId,stock));
    }

    @DeleteMapping("/deleteTest/{productId}")
    void deleteTest(@PathVariable String productId){
        productService.deleteProductTest(productId);
    }
}
