package itmo.blps.lab1.controller;

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

    @PostMapping("/start")
    public ResponseEntity<Order> startDelivery(@RequestParam UUID orderId) {
        Order order = deliveryService.startDelivery(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/update-status")
    public ResponseEntity<Order> updateDeliveryStatus(
            @RequestParam UUID orderId,
            @RequestParam Order.OrderStatus status) {

        Order order = deliveryService.updateDeliveryStatus(orderId, status);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/track/{orderId}")
    public ResponseEntity<Order> trackOrder(@PathVariable UUID orderId) {
        Order order = deliveryService.trackOrder(orderId);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }
}
