package itmo.blps.lab1.listener;

import itmo.blps.lab1.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

@Component
@RequiredArgsConstructor
public class DeliveryStatusListener {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryStatusListener.class);

    @JmsListener(destination = "delivery.status.queue")
    public void processDeliveryStatus(@Payload DeliveryStatusMessage message) {
        logger.info("Processing delivery status update: {}", message);

        // ЛОГИКА
    }
}