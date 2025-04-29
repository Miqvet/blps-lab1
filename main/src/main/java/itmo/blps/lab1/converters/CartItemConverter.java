package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.CartItemDTO;
import itmo.blps.lab1.entity.CartItem;
import itmo.blps.lab1.entity.Product;

public class CartItemConverter {

    public static CartItemDTO toDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }

    public static CartItem fromDTO(CartItemDTO dto, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setProduct(product);
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }
}
