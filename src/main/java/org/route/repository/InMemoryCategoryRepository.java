package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Category;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoryRepository implements CategoryRepository {

    private final Map<String, Category> myCategories = new HashMap<>();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(myCategories.values());
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        if (slug == null)
            return Optional.empty();

        return Optional.ofNullable(myCategories.get(slug));
    }

    @Override
    public List<Category> findByParentSlug(String parentSlug) {
        return myCategories.values().stream()
                .filter(c -> Objects.equals(parentSlug == null ? "" : parentSlug,
                        c.getParentSlug() == null ? "" : c.getParentSlug() ))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Category category) {
        myCategories.put(category.getSlug(), category);
    }

    @Override
    public void deleteBySlug(String slug) {
        myCategories.remove(slug);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return slug != null && myCategories.containsKey(slug);    }

    @PostConstruct
    public void initCategories() {
        // 1. Top-level categories (parentSlug is empty)[cite: 4, 5]
        save(Category.builder().name("Electronics").slug("electronics").parentSlug("").displayOrder(1).active(true).build());
        save(Category.builder().name("Home & Kitchen").slug("home-kitchen").parentSlug("").displayOrder(2).active(true).build());
        save(Category.builder().name("Fashion").slug("fashion").parentSlug("").displayOrder(3).active(false).build());

        // 2. Sub-categories[cite: 4, 5]
        save(Category.builder().name("Mobile Phones").slug("mobile-phones").parentSlug("electronics").displayOrder(1).active(true).build());
        save(Category.builder().name("Laptops").slug("laptops").parentSlug("electronics").displayOrder(2).active(true).build());
        save(Category.builder().name("Accessories").slug("accessories").parentSlug("electronics").displayOrder(3).active(true).build());
        save(Category.builder().name("Cookware").slug("cookware").parentSlug("home-kitchen").displayOrder(1).active(true).build());
        save(Category.builder().name("Lighting").slug("lighting").parentSlug("home-kitchen").displayOrder(2).active(true).build());
    }
}
