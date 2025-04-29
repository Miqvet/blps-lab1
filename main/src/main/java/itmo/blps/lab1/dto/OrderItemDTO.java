package itmo.blps.lab1.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDTO {
    private UUID id;
    private UUID productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}
