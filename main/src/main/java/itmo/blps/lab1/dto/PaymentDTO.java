package itmo.blps.lab1.dto;

import lombok.Data;

import java.util.UUID;

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