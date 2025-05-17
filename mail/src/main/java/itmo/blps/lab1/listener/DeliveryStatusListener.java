package itmo.blps.lab1.listener;

import com.itextpdf.text.DocumentException;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.service.MailService;
import itmo.blps.lab1.service.PdfGenerator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryStatusListener {

    private final RuntimeService runtimeService;

    @JmsListener(destination = "delivery.status.queue")
    public void processDeliveryStatus(@Payload DeliveryStatusMessage message) {
        log.info("Received delivery status message: {}", message);

        Map<String, Object> variables = new HashMap<>();
        variables.put("deliveryStatus", message);

        // Стартуем процесс по ключу (тот, что в модели BPMN)
        runtimeService.startProcessInstanceByKey("process_delivery_status", variables);
    }
}