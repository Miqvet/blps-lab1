package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Tag(name = "Доставка", description = "API для управления доставкой заказов")
@RestController
@AllArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Получить все заказы ожидающие доставки",
            description = "Получает пул заказов которые в данный момент ожидают согласования доставки")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/waiting")
    public ResponseEntity<List<OrderDTO>> getDeliveries() {
        List<OrderDTO> deliveries = deliveryService.getDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @Operation(summary = "Начать доставку",
            description = "Запускает процесс доставки для указанного заказа.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{orderId}/start")
    public ResponseEntity<OrderDTO> startDelivery(
            @Parameter(description = "ID заказа", required = true)
            @PathVariable UUID orderId) {

        Order order = deliveryService.startDelivery(orderId);
        return ResponseEntity.ok(OrderConverter.toDTO(order));
    }

    @Operation(summary = "Обновить статус доставки",
            description = "Обновляет статус доставки для указанного заказа.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateDeliveryStatus(
            @Parameter(description = "ID заказа", required = true)
            @PathVariable UUID orderId,
            @Parameter(description = "Новый статус доставки", required = true)
            @RequestParam Order.OrderStatus status) {

        Order order = deliveryService.updateDeliveryStatus(orderId, status);
        return ResponseEntity.ok(OrderConverter.toDTO(order));
    }

    @Operation(summary = "Отследить заказ",
            description = "Возвращает информацию о текущем статусе доставки указанного заказа.")
    @GetMapping("/{orderId}/track")
    public ResponseEntity<OrderDTO> trackOrder(
            @Parameter(description = "ID заказа", required = true)
            @PathVariable UUID orderId) {
        Order order = deliveryService.trackOrder(orderId);
        return order != null ? ResponseEntity.ok(OrderConverter.toDTO(order)) : ResponseEntity.notFound().build();
    }
}
