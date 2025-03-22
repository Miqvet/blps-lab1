package itmo.blps.lab1.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private List<OrderItemDTO> items;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELED
    }
}