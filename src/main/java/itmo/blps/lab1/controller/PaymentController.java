package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.PaymentConverter;
import itmo.blps.lab1.dto.PaymentDTO;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Платежи", description = "API для управления платежами")
@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Получить статус платежа",
            description = "Возвращает статус платежа для указанного заказа.")
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDTO> getPaymentStatus(
            @Parameter(description = "ID заказа", required = true)
            @PathVariable UUID orderId) {

        Payment payment = paymentService.getPaymentByOrderId(orderId);
        return payment != null ? ResponseEntity.ok(PaymentConverter.toDTO(payment)) : ResponseEntity.notFound().build();
    }

}

