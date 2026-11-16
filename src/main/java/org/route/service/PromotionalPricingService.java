package org.route.service;

import org.route.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PromotionalPricingService implements PricingService {
    BigDecimal discountAmount;

    public PromotionalPricingService(@Value("${store.discountAmount}") BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal getPrice(Product product, int quantity) {
        BigDecimal gross = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal off = gross.multiply(discountAmount).divide(BigDecimal.valueOf(100));
        return gross.subtract(off);
    }
}
