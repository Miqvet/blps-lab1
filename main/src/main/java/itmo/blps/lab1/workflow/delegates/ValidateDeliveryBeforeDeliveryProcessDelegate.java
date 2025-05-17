package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.OrderService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component("validateDeliveryBeforeDeliveryProcessDelegate")
class ValidateDeliveryBeforeDeliveryProcessDelegate implements JavaDelegate {

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        UUID orderId = UUID.fromString((String) delegateExecution.getVariable("orderId"));
        Order order = orderService.findById(orderId);
        Boolean isOk = order.getStatus() == Order.OrderStatus.PENDING;
        delegateExecution.setVariable("orderStatus", order.getStatus().toString());
        delegateExecution.setVariable("isOk", isOk);
        if (!isOk) {
            delegateExecution.setVariable("error_message", "Статус доставки не PENDING");
        }
    }
}