package org.route.model;

import Exceptions.StoreException;
import lombok.Getter;
import lombok.Setter;
import org.route.repository.ProductRepository;
import org.route.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
@Setter
@Getter
public class ShoppingCart {

    private  Integer cartNumber;
    private Customer customer;

    @Autowired
    private ProductRepository productRepository;
    private  ProductService productService;

    // 2. جلب القيمة من ملف الـ Properties مباشرة باستخدام @Value
    @Value("${store.max-quantity-per-product:10}") // الرقم 10 كقيمة افتراضية احترازية
    private int maxQuantityPerProduct;

    private final Map<String, Integer> lines = new HashMap<>();


    public ShoppingCart() {
    }

    public void addItem(String sku, int quantity) {
        if (quantity <= 0)  // if quantity= 0 or if quantity= -1
            return;

        // if stock of product = 0 out of stock
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new StoreException("Product not found: " + sku));
        if (product == null || product.getStockQuantity() <= 0) {
            throw new StoreException("Product out of stock or does not exist: " + sku);
        }

        //if the product is already exist in the cart -> increase the quantity only
        int currentQty = lines.getOrDefault(sku, 0);
        int totalQty = currentQty + quantity;

        // if the quantity exceeds 10 or quantity greater than stock quantity
        if (totalQty > maxQuantityPerProduct || totalQty > product.getStockQuantity()) {
            throw new StoreException("Cannot add quantity. Exceeds max limit or available stock.");
        }

        lines.put(sku, totalQty);
    }

    public BigDecimal subtotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : lines.entrySet()) {
            String sku = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findBySku(sku)
                    .orElseThrow(() -> new StoreException("Product not found: " + sku));
            if (product != null) {
                BigDecimal lineTotal = productService.getPrice(product, quantity);
                sum = sum.add(lineTotal);
            }
        }
        return sum;
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public void clear() {
        lines.clear();
    }


}