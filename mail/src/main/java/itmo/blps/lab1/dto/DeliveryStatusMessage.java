package itmo.blps.lab1.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusMessage implements Serializable {
    private String email;
    private UUID orderId;
    private String status;
    private String message;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return String.format(
                "DeliveryStatusMessage{orderId=%s, status='%s', message='%s', timestamp=%s}",
                orderId, status, message, timestamp
        );
    }
}
