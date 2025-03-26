package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.*;
import itmo.blps.lab1.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    @Transactional
    public Order placeOrder(UUID userId, String address) {
        logger.info("Initiating order placement for user ID: {}", userId);

        Cart cart = cartService.getCartByUserId(userId);

        if (cart.getItems().isEmpty()) {
            logger.error("Cart is empty for user ID: {}", userId);
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setDeliveryAddress(address);

        order.setItems(cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                String errorMsg = "Product " + product.getId() + " is out of stock.";
                logger.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productService.save(product); // Обновление запаса
            return new OrderItem(null, order, product, cartItem.getQuantity(), product.getPrice());
        }).collect(Collectors.toList()));

        order.setTotalPrice(cart.getItems().stream()
                .map(t -> t.getProduct().getPrice().multiply(BigDecimal.valueOf(t.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        orderRepository.save(order);
        cartService.delete(cart); // Очистка корзины после оформления заказа
        logger.info("Order placed successfully for user ID: {}", userId);
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

    public List<Order> findByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
