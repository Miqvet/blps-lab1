package itmo.blps.lab1.service;

import itmo.blps.lab1.config.YandexConnection;
import itmo.blps.lab1.dto.DeliveryStatusMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    @Value("${MAIL_BOX}")
    private String mail;

    private final YandexConnection connection;

    public void sendEmail(DeliveryStatusMessage deliveryStatusMessage){
        if(deliveryStatusMessage.getStatus().equals("DELIVERED")){
            deliveryStatusMessage.setMessage(deliveryStatusMessage.getMessage() + "\n" +
                     "Your order still save till: " + deliveryStatusMessage.getTimestamp());
        }
        this.send(deliveryStatusMessage.getEmail(), mail, deliveryStatusMessage.getMessage());
    }

    public void send(String to, String from, String message) {
        try {
            connection.send(to, from, message);
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
//    //TODO только для теста
//    @Scheduled(fixedDelay = 1 * 60 * 1000)
//    public void send() {
//        try {
//            connection.send("mail", mail, "Страшно");
//            log.info("Email sent successfully to {}", "mail");
//        } catch (Exception e) {
//            log.error("Failed to send email to {}: {}", "mail", e.getMessage());
//            throw new RuntimeException("Failed to send email", e);
//        }
//    }
}
