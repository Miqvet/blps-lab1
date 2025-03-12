package itmo.blps.lab1.service;

import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;
import itmo.blps.lab1.repository.OrderRepository;
import itmo.blps.lab1.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public Payment processPayment(UUID orderId, Payment.PaymentMethod method) {
        Order order = orderService.findById(orderId);

        if (!order.getStatus().equals(Order.OrderStatus.PENDING)) {
            throw new RuntimeException("Order is not available for payment");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(method);
        payment.setPaymentStatus(Payment.PaymentStatus.COMPLETED);
        payment.setTransactionId(UUID.randomUUID().toString());

        order.setStatus(Order.OrderStatus.PAID);
        paymentRepository.save(payment);
        orderService.save(order);

        return payment;
    }

    @Transactional(readOnly = true)
    public Payment getPaymentByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}

