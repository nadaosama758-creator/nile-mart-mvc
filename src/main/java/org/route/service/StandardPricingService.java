package org.route.service;

import org.route.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary

public class StandardPricingService implements PricingService {
    @Override
    public BigDecimal getPrice(Product product, int quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
