package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.OrderItemDTO;
import itmo.blps.lab1.entity.OrderItem;
import itmo.blps.lab1.entity.Product;

public class OrderItemConverter {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        return dto;
    }

    public static OrderItem fromDTO(OrderItemDTO dto, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPriceAtPurchase(dto.getPriceAtPurchase());
        return orderItem;
    }
}