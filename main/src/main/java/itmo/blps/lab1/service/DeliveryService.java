package itmo.blps.lab1.service;

import io.netty.channel.ChannelHandler;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderService orderService;
    private final DeliveryNotificationService notificationService;
    private final Map<UUID, Integer> deliveryCounter = new HashMap<>();

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
                        order.getUser().getEmail(),
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
        notificationService.notifyDeliveryStatus(createDeliveryServiceMessage(order.getUser().getEmail(), orderId, status));
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order trackOrder(UUID orderId) {
        return orderService.findById(orderId);
    }


    private DeliveryStatusMessage createDeliveryServiceMessage(String email, UUID orderId,
                                                               Order.OrderStatus status) {
        String message = generateStatusMessage(status);
        DeliveryStatusMessage statusMessage = new DeliveryStatusMessage(
                email, orderId, status.name(), message, LocalDateTime.now()
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

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void scheduleDeliveryService() {
        var orders = orderService.findByStatus(Order.OrderStatus.DELIVERED);
        for (var order : orders) {
            LocalDateTime deliveryDate = order.getCreatedAt().toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            long hoursUntilDelivery = ChronoUnit.HOURS.between(now, deliveryDate);

            if (hoursUntilDelivery == 24 && !deliveryCounter.containsKey(order.getId())) {
                notificationService.notifyDeliveryStatus(
                        createDeliveryServiceMessage(
                                order.getUser().getEmail(),
                                order.getId(),
                                Order.OrderStatus.SHIPPED
                        )
                );
                deliveryCounter.put(order.getId(), 0);
            } else if (hoursUntilDelivery == 48 &&
                    deliveryCounter.containsKey(order.getId())
                    && deliveryCounter.get(order.getId()) == 0) {
                notificationService.notifyDeliveryStatus(
                        createDeliveryServiceMessage(
                                order.getUser().getEmail(),
                                order.getId(),
                                Order.OrderStatus.SHIPPED
                        )
                );
                deliveryCounter.put(order.getId(), 1);
            } else if (hoursUntilDelivery == 72 &&
                    deliveryCounter.containsKey(order.getId())
                    && deliveryCounter.get(order.getId()) == 1) {
                notificationService.notifyDeliveryStatus(
                        createDeliveryServiceMessage(
                                order.getUser().getEmail(),
                                order.getId(),
                                Order.OrderStatus.SHIPPED
                        )
                );
                deliveryCounter.put(order.getId(), 2);
            } else if (hoursUntilDelivery == 96 &&
                    deliveryCounter.containsKey(order.getId())
                    && deliveryCounter.get(order.getId()) == 2) {
                notificationService.notifyDeliveryStatus(
                        createDeliveryServiceMessage(
                                order.getUser().getEmail(),
                                order.getId(),
                                Order.OrderStatus.CANCELED
                        )
                );
                orderService.cancelOrder(order.getId());
            }
        }
    }
}


