package itmo.blps.lab1.workflow.delegates;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.entity.Order;
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

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf((String) delegateExecution.getVariable("orderStatus"));
        UUID orderId = UUID.fromString((String) delegateExecution.getVariable("orderId"));

        Order order = orderService.findById(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        order.setStatus(orderStatus);
        orderService.updateOrder(order);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(OrderConverter.toDTO(order));
        delegateExecution.setVariable("order", json);
    }
}