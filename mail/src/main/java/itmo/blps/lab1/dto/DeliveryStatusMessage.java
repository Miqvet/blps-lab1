package itmo.blps.lab1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DeliveryStatusMessage implements Serializable {
    private final String email;
    private final UUID orderId;
    private final String status;
    private String message;
    private final LocalDateTime timestamp;

    @JsonCreator
    public DeliveryStatusMessage(
            @JsonProperty("email") String email,
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("status") String status,
            @JsonProperty("message") String message,
            @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.email = email;
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "DeliveryStatusMessage{orderId=%s, status='%s', message='%s', timestamp=%s}",
                orderId, status, message, timestamp
        );
    }
}
