package itmo.blps.lab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.blps.lab1.converters.OrderConverter;
import itmo.blps.lab1.converters.PaymentConverter;
import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.service.OrderPaymentFacade;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import itmo.blps.lab1.service.OrderService;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Tag(name = "Заказы", description = "API для управления заказами пользователей")
@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderPaymentFacade orderPaymentFacade;
    private final RuntimeService runtimeService;

    @Operation(summary = "Оформить заказ",
            description = "Создает новый заказ для указанного пользователя и обрабатывает платеж")
    @PostMapping("")
    //@PreAuthorize("hasAuthority('ORDER_CREATE')")
    public ResponseEntity<?> checkout(
            @Parameter(description = "ID пользователя", required = true)
            @RequestParam UUID userId,
            @Parameter(description = "Адрес доставки", required = true)
            @RequestParam String deliveryAddress,
            @Parameter(description = "Метод оплаты", required = true)
            @RequestParam Payment.PaymentMethod method) {
        String regex = "^[a-zA-Zа-яА-Я0-9 .]+$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(deliveryAddress).matches()) {
            return new ResponseEntity<>("Адрес доставки должен состоять из цифр, букв, пробелов и точек", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);
        variables.put("deliveryAddress", deliveryAddress);
        variables.put("paymentMethod", method);

        runtimeService.startProcessInstanceByKey(
                "order_payment_process",
                variables
        );

        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Получить заказы пользователя",
            description = "Возвращает список заказов для указанного пользователя.")
    @GetMapping("/{userId}")
    //@PreAuthorize("hasAuthority('ORDER_VIEW')")
    public ResponseEntity<List<OrderDTO>> getUserOrders(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable UUID userId) {

        List<OrderDTO> orders = orderService.getUserOrders(userId).stream()
                .map(OrderConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
