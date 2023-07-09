package com.example.demo.api;

import com.example.demo.entities.ProductEntity;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add-a-product")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ResponseEntity<ProductEntity> response = productService.saveProduct(product);
        return response;
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        ResponseEntity<List<ProductEntity>> response = productService.getAllProducts();
        return response;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long productId) {
        ResponseEntity<ProductEntity> response = productService.getProductById(productId);
        return response;
    }
}
