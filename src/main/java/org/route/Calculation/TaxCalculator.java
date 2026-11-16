package org.route.Calculation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

public class TaxCalculator {
    private final BigDecimal vatRate;

    public TaxCalculator(@Value("${store.vat-rate}") BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    public BigDecimal calculateVat(BigDecimal subtotal) {
        return subtotal.multiply(vatRate);
    }
}
