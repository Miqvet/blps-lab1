package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.PaymentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("paymentDelegate")
public class PaymentDelegate implements JavaDelegate {
    @Autowired
    private PaymentService paymentService;

    @Override
    public void execute(DelegateExecution execution) {
        UUID orderId = (UUID) execution.getVariable("orderId");
        Payment.PaymentMethod method = Payment.PaymentMethod.valueOf(
                (String) execution.getVariable("paymentMethod")
        );

        paymentService.processPayment(orderId, method);
    }
}
