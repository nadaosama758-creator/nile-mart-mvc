package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Brand;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository

public class InMemoryBrandRepository implements BrandRepository {

    private final Map<String, Brand> myBrands= new HashMap<>();

    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(myBrands.values());
    }

    @Override
    public Optional<Brand> findBySlug(String slug) {
        if (slug == null)
            return Optional.empty();

        return Optional.ofNullable(myBrands.get(slug));
    }

    @Override
    public void save(Brand brand) {
        myBrands.put(brand.getSlug(), brand);
    }

    @Override
    public void deleteBySlug(String slug) {
        myBrands.remove(slug);
    }

    @Override
    public boolean existsBySlug(String slug) {
         return slug != null && myBrands.containsKey(slug);
    }

    @PostConstruct
    public void initBrands() {
        save(Brand.builder().name("Samsung").slug("samsung").active(true).build());
        save(Brand.builder().name("Asus").slug("asus").active(true).build());
        save(Brand.builder().name("Anker").slug("anker").active(true).build());
        save(Brand.builder().name("Tefal").slug("tefal").active(true).build());
        save(Brand.builder().name("Old Brand").slug("old-brand").active(false).build());
    }
}
