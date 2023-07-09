package com.example.demo.services;

import com.example.demo.entities.ProductEntity;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        saveProduct(new ProductEntity("as",null,
                "as","sa","sa", BigDecimal.ONE,1));
    }



    public ResponseEntity<ProductEntity> saveProduct(ProductEntity product) {
        ProductEntity savedProduct = productRepository.saveAndFlush(product);
        return ResponseEntity.ok(savedProduct);
    }

    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    public ResponseEntity<ProductEntity> getProductById(Long productId) {
        ProductEntity product = productRepository.findById(String.valueOf(productId))
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
        return ResponseEntity.ok(product);
    }


}