package itmo.blps.mail.service;

import itmo.blps.mail.dto.DeliveryStatusMessage;

public interface DeliveryNotificationService {
    void notifyDeliveryStatus(DeliveryStatusMessage message);
}