package itmo.blps.lab1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class CartItemDTO {
    private UUID id;
    private UUID productId;
    private String productName;
    private Integer quantity;
}
