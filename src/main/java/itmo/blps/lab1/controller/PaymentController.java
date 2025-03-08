package itmo.blps.lab1.controller;

import itmo.blps.lab1.converters.PaymentConverter;
import itmo.blps.lab1.dto.PaymentDTO;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;

    // Получить статус платежа
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentStatus(@PathVariable UUID orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return payment != null ? ResponseEntity.ok(PaymentConverter.toDTO(payment)) : ResponseEntity.notFound().build();
    }

    // Обработка платежа
    @PostMapping("/{orderId}")
    public ResponseEntity<String> processPayment(
            @PathVariable UUID orderId,
            @RequestParam Payment.PaymentMethod method) {

        try {
            Payment payment = paymentService.processPayment(orderId, method);
            return ResponseEntity.ok("Payment successful. Transaction ID: " + payment.getTransactionId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Payment failed: " + e.getMessage());
        }
    }
}

