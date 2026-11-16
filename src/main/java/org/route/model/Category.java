package org.route.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    private String name;
    private String slug;
    private  String parentSlug;
    private int displayOrder;
    private Boolean active;

}
