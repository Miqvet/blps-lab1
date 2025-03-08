package itmo.blps.lab1.dto;

import itmo.blps.lab1.entity.Payment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class PaymentDTO {
    private UUID id;
    private UUID orderId;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String transactionId;

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    public enum PaymentMethod {
        CREDIT_CARD,
        PAYPAL,
        APPLE_PAY
    }
}