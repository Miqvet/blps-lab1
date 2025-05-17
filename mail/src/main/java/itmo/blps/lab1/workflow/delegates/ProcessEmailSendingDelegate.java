package itmo.blps.lab1.workflow.delegates;

import itmo.blps.lab1.config.YandexConnection;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.service.PdfGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Component("processEmailSendingDelegate")
public class ProcessEmailSendingDelegate implements JavaDelegate {
    @Value("${MAIL_BOX}")
    private String mail;

    private final YandexConnection connection;
    public void execute(DelegateExecution execution) throws Exception {
        var message = (DeliveryStatusMessage) execution.getVariable("deliveryStatus");
        log.info("Generating PDF for orderId={} and sending to {}", message.getOrderId(), message.getEmail());

        if (message.getStatus().equals("DELIVERED")) {
            message.setMessage(message.getMessage() + "\n" +
                    "Your order still save till: " + message.getTimestamp());
        }

        var pdfFile = PdfGenerator.generateReceipt(message);
        send(message.getEmail(), mail, message.getMessage(), pdfFile);
    }
    public void send(String to, String from, String message, File attach) {
        try {
            connection.send(to, from, message, attach);
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
