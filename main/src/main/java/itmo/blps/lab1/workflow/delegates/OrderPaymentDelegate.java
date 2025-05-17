package itmo.blps.lab1.workflow.delegates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import itmo.blps.lab1.converters.PaymentConverter;
import itmo.blps.lab1.dto.PaymentDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.exception.classes.OrderPaymentException;
import itmo.blps.lab1.service.OrderPaymentFacade;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component("orderPaymentDelegate")
public class OrderPaymentDelegate implements JavaDelegate {
    @Autowired
    private OrderPaymentFacade orderPaymentFacadeService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("PaymentDelegate execute()");
        UUID userId = UUID.fromString((String) execution.getVariable("userId"));
        String address = (String) execution.getVariable("address");
        Payment.PaymentMethod method = Payment.PaymentMethod.valueOf((String) execution.getVariable("method"));
        log.info("PaymentDelegate placeOrderAndProcessPayment() start");
        try {
            Payment payment = orderPaymentFacadeService.placeOrderAndProcessPayment(userId, address, method);
            PaymentDTO dto = PaymentConverter.toDTO(payment);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
            execution.setVariable("payment", json);
            execution.setVariable("orderId", payment.getOrder().getId().toString());
            execution.setVariable("isSuccess", true);
        } catch (Exception ex) {
            execution.setVariable("isSuccess", false);
        }
        log.info("PaymentDelegate placeOrderAndProcessPayment() end");
    }
}
