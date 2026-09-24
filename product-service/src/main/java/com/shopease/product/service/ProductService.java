package com.shopease.product.service;

import com.shopease.product.model.Product;
import com.shopease.product.repositry.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {

        try {
            List<Product> products = productRepository.findAll();

            // MongoDB is connected but no products found
            if (products == null || products.isEmpty()) {
                return getStaticProducts();
            }

            return products;

        } catch (Exception e) {

            // MongoDB connection/problem
            System.out.println("MongoDB unavailable. Returning static products.");
            System.out.println("Error: " + e.getMessage());

            return getStaticProducts();
        }
    }

    public Product getProductById(String productId) {

        return productRepository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }
    
    private List<Product> getStaticProducts() {

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId("1");
        product1.setProductId("P1001");
        product1.setProductName("Mango Pickle");
        product1.setProductPrice(250.0);
        product1.setProductImage("mango-pickle.jpg");
        product1.setCategory("Pickles");
        product1.setDescription("Traditional homemade mango pickle");
        product1.setStock(100);

        Product product2 = new Product();
        product2.setId("2");
        product2.setProductId("P1002");
        product2.setProductName("Red Chilli Pickle");
        product2.setProductPrice(220.0);
        product2.setProductImage("red-chilli-pickle.jpg");
        product2.setCategory("Pickles");
        product2.setDescription("Spicy red chilli pickle");
        product2.setStock(80);

        Product product3 = new Product();
        product3.setId("3");
        product3.setProductId("P1003");
        product3.setProductName("Mixed Pickle");
        product3.setProductPrice(200.0);
        product3.setProductImage("mixed-pickle.jpg");
        product3.setCategory("Pickles");
        product3.setDescription("Traditional mixed vegetable pickle");
        product3.setStock(75);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        return products;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String productId) {

        Product product = productRepository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        productRepository.delete(product);
    }
}
