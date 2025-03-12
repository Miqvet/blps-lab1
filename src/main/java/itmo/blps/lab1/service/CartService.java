package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.entity.CartItem;
import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.repository.CartRepository;
import itmo.blps.lab1.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Cart getCartByUserId(UUID userId) {
        User user = userService.findByUserId(userId);
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public Cart addToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getItems().add(new CartItem(null, cart, product, quantity));
        return cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(UUID userId, UUID cartItemId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }
}

