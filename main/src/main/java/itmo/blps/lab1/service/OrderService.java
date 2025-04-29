package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.*;
import itmo.blps.lab1.exception.classes.EmptyCartException;
import itmo.blps.lab1.exception.classes.InsufficientStockException;
import itmo.blps.lab1.exception.classes.OrderNotFoundException;
import itmo.blps.lab1.exception.classes.ProductNotFoundException;
import itmo.blps.lab1.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final TransactionTemplate transactionTemplate;

    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        ProductService productService,
                        TransactionTemplate transactionTemplate) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.transactionTemplate = transactionTemplate;
    }

    public Order createOrder(UUID userId, String address) {
        try {
            // 1. Проверяем корзину
            Cart cart = cartService.getCartByUserId(userId);
            if (cart.getItems().isEmpty()) {
                throw new EmptyCartException(userId);
            }

            // 2. Создаём заказ
            Order order = new Order();
            order.setUser(cart.getUser());
            order.setStatus(Order.OrderStatus.PENDING);
            order.setDeliveryAddress(address);

            // 3. Резервируем товары атомарно
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cart.getItems()) {
                UUID productId = cartItem.getProduct().getId();
                int quantity = cartItem.getQuantity();

                boolean success = productService.decreaseProductStock(productId, quantity);
                if (!success) {
                    throw new InsufficientStockException(productId, cartItem.getProduct().getStock(), quantity);
                }

                Product product = productService.getProductById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));

                orderItems.add(new OrderItem(
                        null, order, product,
                        quantity, product.getPrice()
                ));
            }

            order.setItems(orderItems);
            order.setTotalPrice(calculateTotal(cart));

            // 4. Сохраняем заказ и очищаем корзину
            Order savedOrder = orderRepository.save(order);
            cartService.delete(cart);
            return savedOrder;
        } catch (EmptyCartException | InsufficientStockException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create order", ex);
        }
    }

    public Object cancelOrder(UUID orderId) {
        return transactionTemplate.execute(status -> {
            try {
                Order order = findOrderOrThrow(orderId);
                order.setStatus(Order.OrderStatus.CANCELED);
                orderRepository.save(order);
                logger.info("Order {} canceled successfully", orderId);
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw new RuntimeException("Failed to cancel order: " + ex.getMessage(), ex);
            }
            return null;
        });
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

    public Order updateOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null for update");
        }

        if (!orderRepository.existsById(order.getId())) {
            throw new OrderNotFoundException(order.getId());
        }

        return orderRepository.save(order);
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order findOrderOrThrow(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
