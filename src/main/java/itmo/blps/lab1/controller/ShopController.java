package itmo.blps.lab1.controller;

import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.service.CartService;
import itmo.blps.lab1.service.OrderService;
import itmo.blps.lab1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/shop")
public class ShopController {

    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;

    // Получить список товаров
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Добавить товар в корзину
    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addToCart(@RequestParam UUID userId, @RequestParam UUID productId, @RequestParam Integer quantity) {
        Cart cart = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    // Получить товары в корзине
    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    // Оформить заказ
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam UUID userId, @RequestParam String deliveryAddress) {
        Order order = orderService.placeOrder(userId, deliveryAddress);
        return ResponseEntity.ok(order);
    }

    // Получить заказы пользователя
    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable UUID userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }
}
