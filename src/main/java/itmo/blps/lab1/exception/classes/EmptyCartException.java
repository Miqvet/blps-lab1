package itmo.blps.lab1.exception.classes;

import java.util.UUID;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(UUID userId) {
        super("Cannot place order - cart is empty for user ID: " + userId);
    }
}
