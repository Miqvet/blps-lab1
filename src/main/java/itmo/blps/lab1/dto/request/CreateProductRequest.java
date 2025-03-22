package itmo.blps.lab1.dto.request;

import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Название товара не может быть пустым")
    @Size(max = 255, message = "Название товара должно быть не длиннее 255 символов")
    private String name;

    @Size(max = 1000, message = "Описание товара должно быть не длиннее 1000 символов")
    private String description;

    @NotNull(message = "Цена товара обязательна для заполнения")
    @DecimalMin(value = "1.00", message = "Цена товара должна быть не менее 1.00")
    private BigDecimal price;

    @NotNull(message = "Количество товара обязательно для заполнения")
    @Min(value = 1, message = "Количество товара не может быть отрицательным")
    private Integer stock;

    @NotBlank(message = "Название категории обязательно для заполнения")
    private String categoryName;
}
