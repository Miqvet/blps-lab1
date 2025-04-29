package itmo.blps.lab1.service;

import itmo.blps.lab1.config.YandexConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final YandexConnection connection;

    public void send(String to, String from, String message) {
        try {
            connection.send("mail", from, "Страшно страшно очень страшно");
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
