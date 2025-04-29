package itmo.blps.mail.service;
import itmo.blps.mail.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 class DeliveryNotificationServiceImpl implements DeliveryNotificationService {
    private final JmsTemplate jmsTemplate;


    public void notifyDeliveryStatus(DeliveryStatusMessage message) {
        jmsTemplate.convertAndSend("delivery.status.queue", message, m -> {
            m.setStringProperty("_type", DeliveryStatusMessage.class.getName());
            return m;
        });
    }
}
