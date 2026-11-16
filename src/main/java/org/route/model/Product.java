package org.route.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data // have all above
//@RequiredArgsConstructor //for final field
public class Product {
    private String sku;
    private String name;
    private BigDecimal price;
    private String category;
    private int stockQuantity;

    //  Assignment 2:
    private String categorySlug;
    private String brandSlug;




}
