package itmo.blps.lab1.service;

import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryService {

    private final OrderService orderService;

    public DeliveryService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public List<OrderDTO> getDeliveries() {
        List<Order> orders = orderService.findByStatus(Order.OrderStatus.PAID);
        return orders.stream()
                .map(OrderConverter::toDTO)
                .toList();
    }

    @Transactional
    public Order startDelivery(UUID orderId) {
        Order order = orderService.findById(orderId);

        if (order.getStatus() != Order.OrderStatus.PAID) {
            throw new RuntimeException("Order is not ready for delivery");
        }

        order.setStatus(Order.OrderStatus.SHIPPED);
        return orderService.save(order);
    }

    @Transactional
    public Order updateDeliveryStatus(UUID orderId, Order.OrderStatus status) {
        Order order = orderService.findById(orderId);
        order.setStatus(status);
        return orderService.save(order);
    }

    @Transactional(readOnly = true)
    public Order trackOrder(UUID orderId) {
        return orderService.findById(orderId);
    }
}


