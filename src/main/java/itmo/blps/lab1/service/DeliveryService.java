package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeliveryService {

    private final OrderRepository orderRepository;

    public DeliveryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order startDelivery(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != Order.OrderStatus.PAID) {
            throw new RuntimeException("Order is not ready for delivery");
        }

        order.setStatus(Order.OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateDeliveryStatus(UUID orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order trackOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElse(null);
    }
}

