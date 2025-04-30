package itmo.blps.lab1.listener;

import com.itextpdf.text.DocumentException;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.service.MailService;
import itmo.blps.lab1.service.PdfGenerator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
@AllArgsConstructor
public class DeliveryStatusListener {

    private MailService mailService;

    @JmsListener(destination = "delivery.status.queue")
    public void processDeliveryStatus(@Payload DeliveryStatusMessage message) throws IOException, DocumentException {
        log.info("Processing delivery status update: {}", message);
        mailService.sendEmail(message);
    }
}