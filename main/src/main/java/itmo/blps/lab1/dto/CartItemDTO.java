package itmo.blps.lab1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CartItemDTO {

    @NotNull(message = "ID элемента корзины обязательно должен быть указан")
    private UUID id;

    @NotNull(message = "ID товара обязательно должен быть указан")
    private UUID productId;

    @NotBlank(message = "Название товара не может быть пустым")
    @Size(max = 255, message = "Название товара должно быть не длиннее 255 символов")
    private String productName;

    @NotNull(message = "Количество товара обязательно для заполнения")
    @Min(value = 1, message = "Количество товара не может быть меньше 1")
    private Integer quantity;
}
