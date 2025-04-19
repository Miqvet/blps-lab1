package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.exception.classes.OrderPaymentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
public class OrderPaymentFacade {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final TransactionTemplate transactionTemplate;

    public OrderPaymentFacade(OrderService orderService,
                              PaymentService paymentService,
                              TransactionTemplate transactionTemplate) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.transactionTemplate = transactionTemplate;
    }

    public Payment placeOrderAndProcessPayment(UUID userId,
                                               String address,
                                               Payment.PaymentMethod method) {
        return transactionTemplate.execute(status -> {
            try {
                // 1. Создаём заказ
                Order order = orderService.createOrder(userId, address);

                // 2. Обрабатываем платеж
                Payment payment = paymentService.processPayment(order.getId(), method);

                return payment;
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw new OrderPaymentException(ex);
            }
        });
    }
}
