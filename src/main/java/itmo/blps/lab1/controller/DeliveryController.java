package itmo.blps.lab1.controller;

import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    // Начать доставку
    @PostMapping("/{orderId}/start")
    public ResponseEntity<OrderDTO> startDelivery(@PathVariable UUID orderId) {
        Order order = deliveryService.startDelivery(orderId);
        return ResponseEntity.ok(OrderConverter.toDTO(order));
    }

    // Обновление статуса доставки
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateDeliveryStatus(
            @PathVariable UUID orderId,
            @RequestParam Order.OrderStatus status) {

        Order order = deliveryService.updateDeliveryStatus(orderId, status);
        return ResponseEntity.ok(OrderConverter.toDTO(order));
    }

    // Отслеживание доставки
    @GetMapping("/{orderId}/track")
    public ResponseEntity<OrderDTO> trackOrder(@PathVariable UUID orderId) {
        Order order = deliveryService.trackOrder(orderId);
        return order != null ? ResponseEntity.ok(OrderConverter.toDTO(order)) : ResponseEntity.notFound().build();
    }
}
