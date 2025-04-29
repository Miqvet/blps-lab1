package itmo.blps.lab1.listener;

import itmo.blps.lab1.dto.DeliveryStatusMessage;
import itmo.blps.lab1.service.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

import java.time.LocalDateTime;

@Component
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class DeliveryStatusListener {

    private MailService mailService;

    @JmsListener(destination = "delivery.status.queue")
    public void processDeliveryStatus(@Payload DeliveryStatusMessage message){
        log.info("Processing delivery status update: {}", message);
        mailService.sendEmail(message);
    }
}