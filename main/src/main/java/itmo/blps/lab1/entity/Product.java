package itmo.blps.lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Название товара не может быть пустым")
    @Size(max = 255, message = "Название товара должно быть не длиннее 255 символов")
    private String name;

    @Column(length = 1000)
    @Size(max = 1000, message = "Описание товара должно быть не длиннее 1000 символов")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Цена товара обязательна для заполнения")
    @DecimalMin(value = "1.00", message = "Цена товара должна быть не менее 1.00")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Количество товара обязательно для заполнения")
    @Min(value = 0, message = "Количество товара не может быть отрицательным")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Категория товара обязательна для заполнения")
    private Category category;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}