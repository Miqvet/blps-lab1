package itmo.blps.lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @NotNull(message = "Корзина должна быть привязана к элементу")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Товар должен быть указан")
    private Product product;

    @Column(nullable = false)
    @NotNull(message = "Количество товара обязательно для заполнения")
    @Min(value = 1, message = "Количество товара не может быть меньше 1")
    private Integer quantity;
}
