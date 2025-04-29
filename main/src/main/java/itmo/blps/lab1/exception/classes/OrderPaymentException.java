package itmo.blps.lab1.exception.classes;

public class OrderPaymentException extends RuntimeException {
    public OrderPaymentException(Throwable cause) {
        super("Order and payment failed: " + cause.getMessage(), cause);
    }
}
