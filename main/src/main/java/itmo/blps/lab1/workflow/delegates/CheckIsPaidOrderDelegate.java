package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component("checkIsPaidOrderDelegate")
public class CheckIsPaidOrderDelegate implements JavaDelegate {

    private final PaymentService paymentService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        UUID orderId = UUID.fromString((String) delegateExecution.getVariable("orderId"));
        Payment payment = paymentService.getPaymentByOrderId(orderId);

        if (payment == null) {
            delegateExecution.setVariable("error_message", "Заказ не найден");
            delegateExecution.setVariable("isOk", false);
            return;
        }

        if (payment.getPaymentStatus() != Payment.PaymentStatus.COMPLETED) {
            delegateExecution.setVariable("error_message", "Заказ не оплачен");
            delegateExecution.setVariable("isOk", false);
            return;
        }

        delegateExecution.setVariable("isOk", true);
    }
}
