package itmo.blps.lab1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public class DeliveryStatusMessage {
    private final UUID orderId;
    private final String status;
    private final String message;
    private final LocalDateTime timestamp;

    @JsonCreator
    public DeliveryStatusMessage(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("status") String status,
            @JsonProperty("message") String message,
            @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Геттеры
    public UUID getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(
                "DeliveryStatusMessage{orderId=%s, status='%s', message='%s', timestamp=%s}",
                orderId, status, message, timestamp
        );
    }
}
