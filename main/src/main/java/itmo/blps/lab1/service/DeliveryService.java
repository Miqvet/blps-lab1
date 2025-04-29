package itmo.blps.lab1.service;

import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final OrderService orderService;
    private final DeliveryNotificationService notificationService;

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
        Order savedOrder = orderService.save(order);

        notificationService.notifyDeliveryStatus(
                createDeliveryServiceMessage(
                        orderId,
                        Order.OrderStatus.SHIPPED
                )
        );

        return savedOrder;
    }

    @Transactional
    public Order updateDeliveryStatus(UUID orderId, Order.OrderStatus status) {
        Order order = orderService.findById(orderId);
        order.setStatus(status);
        Order savedOrder = orderService.save(order);
        notificationService.notifyDeliveryStatus(createDeliveryServiceMessage(orderId, status));
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order trackOrder(UUID orderId) {
        return orderService.findById(orderId);
    }


    private DeliveryStatusMessage createDeliveryServiceMessage(UUID orderId, Order.OrderStatus status) {
        String message = generateStatusMessage(status);
        DeliveryStatusMessage statusMessage = new DeliveryStatusMessage(
                orderId, status.name(), message, LocalDateTime.now()
        );
        return statusMessage;
    }

    private String generateStatusMessage(Order.OrderStatus status) {
        return switch (status) {
            case SHIPPED -> "Your order is on the way!";
            case DELIVERED -> "Your order has been delivered.";
            case CANCELED -> "Your order was cancelled.";
            default -> "Order status updated.";
        };
    }
}


