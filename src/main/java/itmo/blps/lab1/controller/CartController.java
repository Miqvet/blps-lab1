package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.CartConverter;
import itmo.blps.lab1.dto.CartDTO;
import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Управление корзиной", description = "API для работы с корзинами пользователей")
@RestController
@AllArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Получить товары в корзине",
            description = "Возвращает корзину указанного пользователя по его ID.")
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(
            @Parameter(description = "UUID пользователя, чью корзину нужно получить", required = true)
            @PathVariable UUID userId) {

        Cart cart = cartService.getCartByUserId(userId);
        return cart != null ? ResponseEntity.ok(CartConverter.toDTO(cart)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Добавить товар в корзину",
            description = "Добавляет указанный товар в корзину пользователя по заданному количеству.")
    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> addToCart(
            @Parameter(description = "UUID пользователя, которому будет добавлен товар", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "UUID товара, который нужно добавить", required = true)
            @RequestParam UUID productId,
            @Parameter(description = "Количество товара, которое нужно добавить", required = true)
            @RequestParam Integer quantity) {

        Cart cart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(CartConverter.toDTO(cart));
    }
}

