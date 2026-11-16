package org.route;

import org.route.Calculation.FreeShippingCalculator;
import org.route.Calculation.ShippingCalculator;
import org.route.Calculation.ZoneShippingCalculator;
import org.route.service.ShippingQuoteService;

import java.math.BigDecimal;

public class ObjectFactory {

    /*public static ProductService getProductService(String pricingType){
        if(pricingType.equalsIgnoreCase("standard"))
            return new ProductService(new StandardPricingService(),new InMemoryRepository());
        else if (pricingType.equalsIgnoreCase("promotional"))
            return new ProductService(new PromotionalPricingService(BigDecimal.valueOf(10)),new InMemoryRepository());
        else
            return null;
    }*/

    public ShippingQuoteService createShippingQuoteService() {
        ShippingCalculator defaultCalculator = new ZoneShippingCalculator();
        ShippingCalculator freeCalculator = new FreeShippingCalculator();

        BigDecimal threshold = new BigDecimal("5000");

        return new ShippingQuoteService(defaultCalculator, freeCalculator, threshold);
    }
}
