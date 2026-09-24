package com.shopease.product.controller;

import com.shopease.product.model.Product;
import com.shopease.product.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(
            @PathVariable String productId) {

        return ResponseEntity.ok(
                productService.getProductById(productId)
        );
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product) {

        return ResponseEntity.ok(
                productService.createProduct(product)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
