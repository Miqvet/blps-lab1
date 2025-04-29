package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.exception.classes.InsufficientStockException;
import itmo.blps.lab1.exception.classes.PaymentException;
import itmo.blps.lab1.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final TransactionTemplate transactionTemplate;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService, TransactionTemplate transactionTemplate) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.transactionTemplate = transactionTemplate;
    }

    public Payment processPayment(UUID orderId, Payment.PaymentMethod method) {
        try {
            // 1. Получаем заказ
            Order order = orderService.findById(orderId);

            if (order.getStatus() != Order.OrderStatus.PENDING) {
                throw new IllegalStateException(
                        "Order " + orderId + " cannot be paid (status: " + order.getStatus() + ")"
                );
            }

            // 2. Создаём платёж
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentMethod(method);
            payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
            payment.setTransactionId(UUID.randomUUID().toString());

            // 3. Обновляем статус заказа
            order.setStatus(Order.OrderStatus.PAID);
            orderService.updateOrder(order);

            // 4. Сохраняем платёж
            return paymentRepository.save(payment);
        } catch (IllegalStateException | InsufficientStockException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PaymentException("Payment failed", ex);
        }
    }

    public Payment getPaymentByOrderId(UUID orderId) {
        return transactionTemplate.execute(status -> {
            try {
                return paymentRepository.findByOrderId(orderId)
                        .orElseThrow(() -> new RuntimeException("Payment not found"));
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw new PaymentException("Payment failed", ex);
            }
        });
    }
}

