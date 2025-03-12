package itmo.blps.lab1.service;

import io.jsonwebtoken.lang.Collections;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.converters.OrderItemConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private final OrderRepository orderRepository;

    public DeliveryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<OrderDTO> getDeliveries() {
        List<Order> orders = orderRepository.findByStatus(Order.OrderStatus.PAID);
        return orders.stream()
                .map(OrderConverter::toDTO)
                .toList();
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

