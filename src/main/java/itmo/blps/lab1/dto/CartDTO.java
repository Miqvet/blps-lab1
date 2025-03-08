package itmo.blps.lab1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Data
public class CartDTO {
    private UUID id;
    private UUID userId;
    private List<CartItemDTO> items;
}