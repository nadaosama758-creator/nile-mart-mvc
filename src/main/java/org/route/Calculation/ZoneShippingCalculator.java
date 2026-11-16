package org.route.Calculation;

import org.route.model.Governorate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary

public class ZoneShippingCalculator implements ShippingCalculator {

    @Override
    public BigDecimal calculate(Governorate g) {
        return g.getZone().getFee();
    }
}
