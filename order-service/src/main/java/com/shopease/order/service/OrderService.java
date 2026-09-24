package com.shopease.order.service;

import com.shopease.order.model.Order;
import com.shopease.order.model.OrderItem;
import com.shopease.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order createOrder(Order order) {

		order.setOrderId("ORD-" + UUID.randomUUID());

		order.setOrderDate(LocalDateTime.now());

		order.setStatus("CREATED");

		calculateTotal(order);

		return orderRepository.save(order);
	}

	public Order getOrder(String orderId) {

		try {

			return orderRepository.findByOrderId(orderId).orElseGet(() -> getStaticOrder(orderId));

		} catch (Exception e) {

			System.out.println("MongoDB unavailable. Returning static order.");
			System.out.println("Error: " + e.getMessage());

			return getStaticOrder(orderId);
		}
	}

	private Order getStaticOrder(String orderId) {

		Order order = new Order();

		order.setId("ORDER-001");
		order.setOrderId(orderId);
		order.setUserId("U1001");
		order.setPaymentMethod("COD");
		order.setStatus("CREATED");
		order.setOrderDate(LocalDateTime.now());

		List<OrderItem> items = new ArrayList<>();

		OrderItem item1 = new OrderItem();
		item1.setProductId("P1001");
		item1.setProductName("Mango Pickle");
		item1.setPrice(250.0);
		item1.setQuantity(2);
		item1.setTotalPrice(500.0);

		OrderItem item2 = new OrderItem();
		item2.setProductId("P1002");
		item2.setProductName("Red Chilli Pickle");
		item2.setPrice(220.0);
		item2.setQuantity(1);
		item2.setTotalPrice(220.0);

		items.add(item1);
		items.add(item2);

		order.setItems(items);
		order.setTotalAmount(720.0);

		return order;
	}

	public List<Order> getOrdersByUser(String userId) {

		return orderRepository.findByUserId(userId);
	}

	public Order updateStatus(String orderId, String status) {

		Order order = getOrder(orderId);

		order.setStatus(status);

		return orderRepository.save(order);
	}

	private void calculateTotal(Order order) {

		double total = 0;

//        if (order.getItems() != null) {
//
//            for (OrderItem item : order.getItems()) {
//
//                double itemTotal =
//                        item.getTotalPrice() * item.getQuantity();
//
//                item.setPrice(itemTotal);
//
//                total += itemTotal;
//            }
//        }

		order.setTotalAmount(total);
	}
}
