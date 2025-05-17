package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.OrderItem;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component("getProductsInOrderDelegate")
public class GetProductsInOrderDelegate implements JavaDelegate {

    private final OrderService orderService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        UUID orderId = UUID.fromString((String) delegateExecution.getVariable("orderId"));
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        List<String> products = order.getItems().stream()
                .map(OrderItem::getProduct)
                .map(Product::getName)
                .toList();
        StringJoiner stringJoiner = new StringJoiner("\n");
        products.forEach(stringJoiner::add);
        delegateExecution.setVariable("products", stringJoiner.toString());
    }
}
