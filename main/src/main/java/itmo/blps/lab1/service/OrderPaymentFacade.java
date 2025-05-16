package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.exception.classes.OrderPaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Slf4j
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
                log.info("OrderPaymentFacade transactions started");
                // 1. Создаём заказ
                Order order = orderService.createOrder(userId, address);
                log.info("OrderPaymentFacade order");
                // 2. Обрабатываем платеж
                Payment payment = paymentService.processPayment(order.getId(), method);
                log.info("OrderPaymentFacade payment");

                return payment;
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw new OrderPaymentException(ex);
            }
        });
    }
}
