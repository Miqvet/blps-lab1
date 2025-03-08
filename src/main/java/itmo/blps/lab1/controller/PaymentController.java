package itmo.blps.lab1.controller;

import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;

    // Обработка платежа
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(
            @RequestParam UUID orderId,
            @RequestParam Payment.PaymentMethod method) {

        try {
            Payment payment = paymentService.processPayment(orderId, method);
            return ResponseEntity.ok("Payment successful. Transaction ID: " + payment.getTransactionId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }
}

