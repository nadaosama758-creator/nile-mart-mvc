package org.route.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.route.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {

   private final Map<String, Product> myProducts = new HashMap<>();

    @Override
    public  List<Product> findAll() {
        return new ArrayList<>(myProducts.values());
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.ofNullable(myProducts.get(sku));
    }

    public void save(Product product) {
        myProducts.put(product.getSku(), product);
    }

    @PostConstruct
    public void initProducts() {
        Product p1 = Product.builder()
                .name("Smartphone X5 128GB")
                .sku("PH-X5-128")
                .price(BigDecimal.valueOf(16000.00))
                .categorySlug("mobile-phones")
                .brandSlug("samsung")
                .stockQuantity(12)
                .build();
        save(p1); //To Save Product In Map

        Product p2 = Product.builder()
                .name("Fast Charger 20W")
                .sku("Acc-CHG-20W")
                .price(BigDecimal.valueOf(650.00))
                .categorySlug("accessories")
                .brandSlug("samsung")
                .stockQuantity(40)
                .build();
        save(p2);

        Product p3 = Product.builder()
                .name("Wireless Earbuds")
                .sku("ACC-BUDS-6")
                .price(BigDecimal.valueOf(1100.00))
                .categorySlug("accessories")
                .brandSlug("samsung")
                .stockQuantity(8)
                .build();
        save(p3);

        Product p4 = Product.builder()
                .name("Cotton Galabeya")
                .sku("FSH-GAL-001")
                .price(BigDecimal.valueOf(900.00))
                .categorySlug("cookware")
                .brandSlug("")
                .stockQuantity(25)
                .build();
        save(p4);

        Product p5 = Product.builder()
                .name("Non-stick Frying Pan 28cm")
                .sku("HOM-PAN-28")
                .price(BigDecimal.valueOf(1200.00))
                .categorySlug("cookware")
                .brandSlug("tefal")
                .stockQuantity(3)
                .build();
        save(p5);

        Product p6 = Product.builder()
                .name("Wooden Spice Rack")
                .sku("HOM-SPC-RCK")
                .price(BigDecimal.valueOf(750.00))
                .categorySlug("cookware")
                .brandSlug("")
                .stockQuantity(10)
                .build();
        save(p6);

        Product p7 = Product.builder()
                .name("Sun Cream SPF50")
                .sku("BTY-SUN-50")
                .price(BigDecimal.valueOf(350.00))
                .categorySlug("skincare") // sub-category تحت beauty[cite: 5]
                .brandSlug("")
                .stockQuantity(0)
                .build();
        save(p7);

        Product p8 = Product.builder()
                .name("The Cairo Trilogy")
                .sku("BK-CAI_TRI")
                .price(BigDecimal.valueOf(450.00))
                .categorySlug("novels")
                .brandSlug("")
                .stockQuantity(15)
                .build();
        save(p8);

        System.out.println("[DEBUG] Initialized products count: " + myProducts.size());
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Catalog closed");
    }


}

