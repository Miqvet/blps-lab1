package itmo.blps.lab1.dto;

import itmo.blps.lab1.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private List<OrderItemDTO> items;
    private BigDecimal totalPrice;
    private String deliveryAddress;
    private OrderStatus status;
    private String trackingNumber;

    public enum OrderStatus {
        PENDING,
        PAID,
        SHIPPED,
        DELIVERED,
        CANCELED
    }
}