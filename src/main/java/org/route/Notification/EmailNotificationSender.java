package org.route.Notification;

import org.route.model.Customer;
import org.springframework.stereotype.Component;

@Component

public class EmailNotificationSender implements NotificationSender {

    @Override
    public void send(Customer customer, String message) {
        if (customer.email() != null || !customer.email().isBlank()) // if email = null & email is empty
            System.out.println("Sending Email to " + customer.email() + ": " + message);
        else
            System.out.println("No email provided for " + customer.fullName() + ", skipping email notification.");
    }
}
