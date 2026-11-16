package org.route.service;

import org.route.repository.BrandRepository;
import org.route.model.Brand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public List<Brand> getActiveBrands() {
        return brandRepository.findAll().stream()
                .filter(Brand::isActive)
                .collect(Collectors.toList());
    }

    public Optional<Brand> getBySlug(String slug) {
        return brandRepository.findBySlug(slug);
    }

    public void saveBrand(Brand brand) {
        validateBrand(brand);
        brandRepository.save(brand);
    }

    private void validateBrand(Brand brand) {
        if (brand.getSlug() == null || !brand.getSlug().matches("^[a-z0-9-]+$")) {
            throw new IllegalArgumentException("Invalid slug format. Use lowercase letters, digits, and hyphens only.");
        }

        // 2. التحقق من عدم تكرار الاسم (ignoring case)
        boolean nameExists = brandRepository.findAll().stream()
                .anyMatch(b -> !b.getSlug().equals(brand.getSlug()) &&
                        b.getName().equalsIgnoreCase(brand.getName()));
        if (nameExists) {
            throw new IllegalArgumentException("Brand name already exists.");
        }
    }
}