package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.service.CartService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidateCartDelegate implements JavaDelegate {
    @Autowired
    private CartService cartService;

    @Override
    public void execute(DelegateExecution execution) {
        UUID userId = (UUID) execution.getVariable("userId");
        Cart cart = cartService.getCartByUserId(userId);

        if (cart.getItems().isEmpty()) {
            throw new BpmnError("EMPTY_CART");
        }

        execution.setVariable("cartValid", true);
    }
}

