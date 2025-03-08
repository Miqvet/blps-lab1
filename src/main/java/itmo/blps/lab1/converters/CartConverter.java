package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.CartDTO;
import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.entity.CartItem;
import itmo.blps.lab1.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class CartConverter {

    public static CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setItems(cart.getItems().stream()
                .map(CartItemConverter::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public static Cart fromDTO(CartDTO dto, User user, List<CartItem> items) {
        Cart cart = new Cart();
        cart.setId(dto.getId());
        cart.setUser(user);
        cart.setItems(items);
        return cart;
    }

}