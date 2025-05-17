package itmo.blps.lab1.workflow.delegates;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.DeliveryService;
import itmo.blps.lab1.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component("updateDeliveryStatusDelegate")
class UpdateDeliveryStatusDelegate implements JavaDelegate {

    private final DeliveryService deliveryService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf((String) delegateExecution.getVariable("orderStatus"));
        UUID orderId = UUID.fromString((String) delegateExecution.getVariable("orderId"));

        String newOrderStatus = (String) delegateExecution.getVariable("orderStatus_new");

        if (newOrderStatus != null) orderStatus = Order.OrderStatus.valueOf(newOrderStatus);

        Order order = deliveryService.updateDeliveryStatus(orderId, orderStatus);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(OrderConverter.toDTO(order));
        delegateExecution.setVariable("orderStatus", orderStatus.name());
        delegateExecution.setVariable("order", json);
        delegateExecution.setVariable("isShipped", order.getStatus() == Order.OrderStatus.DELIVERED);
    }
}