package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.OrderDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.OrderItem;
import itmo.blps.lab1.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setItems(order.getItems().stream()
                .map(OrderItemConverter::toDTO)
                .collect(Collectors.toList()));
        dto.setTotalPrice(order.getTotalPrice());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setStatus(OrderDTO.OrderStatus.valueOf(order.getStatus().name()));
        return dto;
    }

    public static Order fromDTO(OrderDTO dto, User user, List<OrderItem> items) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(user);
        order.setItems(items);
        order.setTotalPrice(dto.getTotalPrice());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setStatus(Order.OrderStatus.valueOf(dto.getStatus().name()));
        return order;
    }
}