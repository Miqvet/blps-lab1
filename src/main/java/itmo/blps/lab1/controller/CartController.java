package itmo.blps.lab1.controller;

import itmo.blps.lab1.converters.CartConverter;
import itmo.blps.lab1.dto.CartDTO;
import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    // Получить товары в корзине
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return cart != null ? ResponseEntity.ok(CartConverter.toDTO(cart)) : ResponseEntity.notFound().build();
    }

    // Добавить товар в корзину
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addToCart(
            @PathVariable UUID userId,
            @RequestParam UUID productId,
            @RequestParam Integer quantity) {

        Cart cart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(CartConverter.toDTO(cart));
    }
}

