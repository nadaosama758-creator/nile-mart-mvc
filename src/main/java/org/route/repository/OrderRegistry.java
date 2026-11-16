package org.route.repository;

import jakarta.annotation.PreDestroy;
import org.route.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component

public class OrderRegistry {
    private  final List<Order> orders = new ArrayList<>();

    public String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void save(Order order) {
        orders.add(order);
    }

    @PreDestroy
    public void printSummary() {
        BigDecimal totalRevenue = orders.stream()
                .map(Order::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("End of day: " + orders.size() + " orders, revenue " + totalRevenue + " EGP.");
    }

}
