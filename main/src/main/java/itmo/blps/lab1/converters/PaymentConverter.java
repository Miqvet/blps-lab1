package itmo.blps.lab1.converters;

import itmo.blps.lab1.dto.PaymentDTO;
import itmo.blps.lab1.entity.Order;
import itmo.blps.lab1.entity.Payment;

public class PaymentConverter {

    public static PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrder().getId());
        dto.setPaymentStatus(PaymentDTO.PaymentStatus.valueOf(payment.getPaymentStatus().name()));
        dto.setPaymentMethod(PaymentDTO.PaymentMethod.valueOf(payment.getPaymentMethod().name()));
        dto.setTransactionId(payment.getTransactionId());
        return dto;
    }

    public static Payment fromDTO(PaymentDTO dto, Order order) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setOrder(order);
        payment.setPaymentStatus(Payment.PaymentStatus.valueOf(dto.getPaymentStatus().name()));
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(dto.getPaymentMethod().name()));
        payment.setTransactionId(dto.getTransactionId());
        return payment;
    }

}