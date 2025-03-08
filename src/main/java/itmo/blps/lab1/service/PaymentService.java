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
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Payment processPayment(UUID orderId, Payment.PaymentMethod method) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

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
        orderRepository.save(order);

        return payment;
    }

    @Transactional(readOnly = true)
    public Payment getPaymentByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

}

