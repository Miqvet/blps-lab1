package itmo.blps.lab1.exception.classes;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(UUID productId, int available, int requested) {
        super(String.format("Insufficient stock for product %s (available: %d, requested: %d)",
                productId, available, requested));
    }
}
