package itmo.blps.lab1.service;

import itmo.blps.lab1.dto.DeliveryStatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MailService mailService;

    @Value("${spring.mail.box}")
    private String mail;

    public void sendEmail(DeliveryStatusMessage deliveryStatusMessage) throws Exception {
        var userText = deliveryStatusMessage.getMessage();
//        if (deliveryStatusMessage.getStatus() == OrderStatus.PENDING) {
//            byte[] pdfContentForCustomer = pdfGenerationService.generatePfdToCustomer(orderMessageDto);
//            mailService.send(orderMessageDto.getCustomerEmail(), mail, pdfContentForCustomer);
//        }
        mailService.send(deliveryStatusMessage.getEmail(), mail, deliveryStatusMessage.getMessage());
    }
}
