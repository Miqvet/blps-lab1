package itmo.blps.lab1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryNotificationService {
    private final JmsTemplate jmsTemplate;


    public void notifyDeliveryStatus(UUID orderId, Order.OrderStatus status) {
        String message = generateStatusMessage(status);
        DeliveryStatusMessage statusMessage = new DeliveryStatusMessage(
                orderId, status.name(), message, LocalDateTime.now()
        );
        jmsTemplate.convertAndSend("delivery.status.queue", statusMessage, m -> {
            m.setStringProperty("_type", DeliveryStatusMessage.class.getName());
            return m;
        });
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