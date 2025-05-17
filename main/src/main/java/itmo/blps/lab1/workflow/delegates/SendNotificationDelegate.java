package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.DeliveryNotificationService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component("sendNotificationDelegate")
@RequiredArgsConstructor
public class SendNotificationDelegate implements JavaDelegate {

    private final DeliveryNotificationService notificationService;

    @Override
    public void execute(DelegateExecution execution) {
        String email = (String) execution.getVariable("email");
        String orderIdStr = (String) execution.getVariable("orderId");
        String statusStr = (String) execution.getVariable("status");

        UUID orderId = UUID.fromString(orderIdStr);
        Order.OrderStatus status = Order.OrderStatus.valueOf(statusStr);

        String message = switch (status) {
            case SHIPPED -> "Your order is on the way!";
            case DELIVERED -> "Your order has been delivered.";
            case CANCELED -> "Your order was cancelled.";
            default -> "Order status updated.";
        };

        DeliveryStatusMessage statusMessage = new DeliveryStatusMessage(
                email,
                orderId,
                status.name(),
                message,
                LocalDateTime.now()
        );

        notificationService.notifyDeliveryStatus(statusMessage);
    }
}
