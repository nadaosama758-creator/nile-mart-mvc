package org.route.Notification;

import org.route.model.Customer;

public interface NotificationSender {
    void send(Customer customer, String message);
}
