package org.route.repository;

import org.route.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    List<Brand> findAll();

    Optional<Brand> findBySlug(String slug);

    void save(Brand brand);

    void deleteBySlug(String slug);

    boolean existsBySlug(String slug);
}
