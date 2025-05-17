package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.DeliveryNotificationService;
import itmo.blps.lab1.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component("checkDeliveryStatusDelegate")
@RequiredArgsConstructor
public class CheckDeliveryStatusDelegate implements JavaDelegate {

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("aaaaaaaaa");
        List<Order> orders = orderService.findByStatus(Order.OrderStatus.DELIVERED);
        LocalDateTime now = LocalDateTime.now();

        for (Order order : orders) {
            long hours = ChronoUnit.HOURS.between(order.getCreatedAt().toLocalDateTime(), now);
            UUID orderId = order.getId();
            String email = order.getUser().getEmail();
            Order.OrderStatus status = null;

            if (hours == 24 || hours == 48 || hours == 72) {
                status = Order.OrderStatus.SHIPPED;
            } else if (hours == 96) {
                status = Order.OrderStatus.CANCELED;
            }

            if (status != null) {
                // Положим данные в process variables
                execution.setVariable("email", email);
                execution.setVariable("orderId", orderId.toString());
                execution.setVariable("status", status.name());
                return; // завершаем после первого подходящего заказа
            }
        }
    }
}