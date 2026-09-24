package com.shopease.order.controller;

import com.shopease.order.model.Order;
import com.shopease.order.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order) {

        return ResponseEntity.ok(
                orderService.createOrder(order)
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(
                orderService.getOrder(orderId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId)
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable String orderId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                orderService.updateStatus(
                        orderId,
                        status
                )
        );
    }
}
