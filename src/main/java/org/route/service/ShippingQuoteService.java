package org.route.service;

import org.route.model.Governorate;
import org.route.Calculation.ShippingCalculator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShippingQuoteService {

    private final ShippingCalculator defaultCalculator; //zone Shipping Calculator
    private final ShippingCalculator freeCalculator;
    private final BigDecimal threshold;

    public ShippingQuoteService( ShippingCalculator defaultCalculator,
                                 @Qualifier("freeShipping") ShippingCalculator freeCalculator,
                                 @Value("${store.free-shipping-threshold}") BigDecimal threshold ) {
        this.defaultCalculator = defaultCalculator;
        this.freeCalculator = freeCalculator;
        this.threshold = threshold;
    }

    //Shipping depends on the governorate’s zone (SHIP-01). Shipping is free when
    //the subtotal is at least 5,000 EGP and the zone is eligible (SHIP-02):

    public BigDecimal quote(Governorate g, BigDecimal subtotal){
        if(subtotal.compareTo(threshold)>=0 && g.getZone().getFreeShipping())
            return freeCalculator.calculate(g);

        return defaultCalculator.calculate(g);
    }
}
