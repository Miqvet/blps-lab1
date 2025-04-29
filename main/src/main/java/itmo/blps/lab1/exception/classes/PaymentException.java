package itmo.blps.lab1.exception.classes;

public class PaymentException extends RuntimeException {
    public PaymentException(String paymentFailed, Exception ex) {
        super(paymentFailed, ex);
    }
}
