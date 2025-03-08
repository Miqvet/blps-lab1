package itmo.blps.lab1.controller;

import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    // Оформить заказ
    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(@RequestParam UUID userId, @RequestParam String deliveryAddress) {
        Order order = orderService.placeOrder(userId, deliveryAddress);
        return ResponseEntity.ok(OrderConverter.toDTO(order));
    }

    // Получить заказы пользователя
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable UUID userId) {
        List<OrderDTO> orders = orderService.getUserOrders(userId).stream()
                .map(OrderConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
