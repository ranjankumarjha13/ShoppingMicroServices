package com.shopease.cart.service;

import com.shopease.cart.model.Cart;
import com.shopease.cart.model.CartItem;
import com.shopease.cart.repository.CartRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCart(String userId) {

        try {

            return cartRepository.findByUserId(userId)
                    .orElseGet(() -> getStaticCart(userId));

        } catch (Exception e) {

            System.out.println("MongoDB unavailable. Returning static cart.");
            System.out.println("Error: " + e.getMessage());

            return getStaticCart(userId);
        }
    }
    
    private Cart getStaticCart(String userId) {

        Cart cart = new Cart();

        cart.setId("CART-001");
        cart.setUserId(userId);

        List<CartItem> items = new ArrayList<>();

        CartItem item1 = new CartItem();
        item1.setProductId("P1001");
        item1.setProductName("Mango Pickle");
        item1.setPrice(250.0);
        item1.setQuantity(2);
        item1.setTotalPrice(500.0);

        CartItem item2 = new CartItem();
        item2.setProductId("P1002");
        item2.setProductName("Red Chilli Pickle");
        item2.setPrice(220.0);
        item2.setQuantity(1);
        item2.setTotalPrice(220.0);

        items.add(item1);
        items.add(item2);

        cart.setItems(items);
        cart.setTotalAmount(720.0);

        return cart;
    }

    public Cart addToCart(
            String userId,
            CartItem newItem) {

        Cart cart = getCart(userId);

        boolean productExists = false;

        for (CartItem item : cart.getItems()) {

            if (item.getProductId()
                    .equals(newItem.getProductId())) {

                item.setQuantity(
                        item.getQuantity()
                                + newItem.getQuantity()
                );

                item.setTotalPrice(
                        item.getPrice()
                                * item.getQuantity()
                );

                productExists = true;
                break;
            }
        }

        if (!productExists) {
            newItem.setTotalPrice(
                    newItem.getPrice()
                            * newItem.getQuantity()
            );

            cart.getItems().add(newItem);
        }

        calculateTotal(cart);

        return cartRepository.save(cart);
    }

    public Cart updateQuantity(
            String userId,
            String productId,
            int quantity) {

        Cart cart = getCart(userId);

        for (CartItem item : cart.getItems()) {

            if (item.getProductId()
                    .equals(productId)) {

                item.setQuantity(quantity);

                item.setTotalPrice(
                        item.getPrice()
                                * quantity
                );

                break;
            }
        }

        calculateTotal(cart);

        return cartRepository.save(cart);
    }

    public Cart removeFromCart(
            String userId,
            String productId) {

        Cart cart = getCart(userId);

        cart.getItems().removeIf(
                item -> item.getProductId()
                        .equals(productId)
        );

        calculateTotal(cart);

        return cartRepository.save(cart);
    }

    private void calculateTotal(Cart cart) {

        double total = 0;

        for (CartItem item : cart.getItems()) {
            total += item.getTotalPrice();
        }

        cart.setTotalAmount(total);
    }
}
