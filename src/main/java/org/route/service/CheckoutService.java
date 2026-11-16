package org.route.service;

import Exceptions.StoreException;
import lombok.RequiredArgsConstructor;
import org.route.Calculation.TaxCalculator;
import org.route.model.Governorate;
import org.route.model.Order;
import org.route.model.Product;
import org.route.model.ShoppingCart;
import org.route.Notification.NotificationSender;
import org.route.repository.OrderRegistry;
import org.route.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class CheckoutService {
    private final ProductService productService;
    private final ProductRepository  productRepository;
    private final ShippingQuoteService shippingQuoteService;
    private final TaxCalculator taxCalculator;
    private final OrderRegistry orderRegistry;
    private final List<NotificationSender> notificationSenderList ;

    public Order placeOrder(ShoppingCart cart, Governorate governorate){

        // >>>>>>>>>>>> 1. rejects an empty cart >>>>>>>>>>>>>>>>>>>>

        if(cart.isEmpty())
            throw new StoreException("Cannot checkout an empty cart.");

        // >>>>>>>>>>>> 1.5. rejects if customer is missing >>>>>>>>>>>>>>>>>>>>

        if(cart.getCustomer() == null)
            throw new StoreException("Customer information is missing in the cart!");


        // >>>>>>>>>>>>>>>>> 2. re-checks all stock >>>>>>>>>>>>>>>>>>>

        for(Map.Entry<String, Integer> entry : cart.getLines().entrySet()){
            Product product=productRepository.findBySku(entry.getKey())
                    .orElseThrow(() -> new StoreException("Product not found: " + entry.getKey()));
            if(product == null || product.getStockQuantity() < entry.getValue()) {
                throw new StoreException("Insufficient stock for product: " + entry.getKey());
            }
        }

        // >>>>>>>>>>>>>>>>>>>>> 3. calculates the totals >>>>>>>>>>>>>>

        BigDecimal subtotal = cart.subtotal();
        BigDecimal vat = taxCalculator.calculateVat(subtotal);
        BigDecimal shippingFee = shippingQuoteService.quote(governorate, subtotal);
        BigDecimal total = subtotal.add(vat).add(shippingFee);

       // >>>>>>>>>>>>>>>>>>> 4. reduces stock >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        for (Map.Entry<String, Integer> entry : cart.getLines().entrySet()) {
            productService.reduceStock(entry.getKey(), entry.getValue());
        }

        // >>>>>>>>>>>>>>>>>>>>>> 5. saves the order >>>>>>>>>>>>>>>>>>>>>>>

        String orderNumber = orderRegistry.generateOrderNumber();
        Order order = new Order(orderNumber, cart.getCustomer(), governorate, Map.copyOf(cart.getLines()), subtotal,
                                vat, shippingFee, total);
        orderRegistry.save(order);

        // >>>>>>>>>>>>>>>>> 6. notifies through every sender in the list >>>>>>>>>>>>>>>>>>>
        String message = "Your order " + orderNumber + " has been placed successfully. Total: " + total + " EGP.";
        for (NotificationSender sender : notificationSenderList) {
            sender.send(cart.getCustomer(), message);
        }

        // >>>>>>>>>>>>>>> 7. clears the cart >>>>>>>>>>>>>>>>>>>>>>>

        cart.clear();

        return order;

        }


}
