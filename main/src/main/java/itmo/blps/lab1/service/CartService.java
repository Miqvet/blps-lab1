package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Cart;
import itmo.blps.lab1.entity.CartItem;
import itmo.blps.lab1.entity.User;
import itmo.blps.lab1.entity.Product;
import itmo.blps.lab1.repository.CartRepository;
import itmo.blps.lab1.mail.service.auth.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;

    public CartService(CartRepository cartRepository, ProductService productService, UserService userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
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

    public void delete(Cart entity) {
        cartRepository.delete(entity);
    }

    @Transactional
    public void delete(UUID userId) {
        Cart cart = getCartByUserId(userId);
        cartRepository.delete(cart);
    }

    @Transactional
    public Cart addToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

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

