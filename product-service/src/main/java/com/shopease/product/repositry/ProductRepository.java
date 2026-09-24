package com.shopease.product.repositry;

import com.shopease.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository
        extends MongoRepository<Product, String> {

    Optional<Product> findByProductId(String productId);
}
