package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.*;
import itmo.blps.lab1.repository.CartRepository;
import itmo.blps.lab1.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartService cartService, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Order placeOrder(UUID userId, String address) {
        Cart cart = cartService.getCartByUserId(userId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setDeliveryAddress(address);
        order.setItems(cart.getItems().stream().map(cartItem ->
                new OrderItem(null, order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getPrice())
        ).collect(Collectors.toList()));
        order.setTotalPrice(cart.getItems().stream().map(t -> t.getProduct().getPrice().multiply(BigDecimal.valueOf(t.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        orderRepository.save(order);
        cartRepository.delete(cart); // Очистка корзины после оформления заказа
        return order;
    }

    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Order.OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    // Получить заказы пользователя
    public List<Order> getUserOrders(UUID userId) {
        return orderRepository.findByUserId(userId);
    }
}
