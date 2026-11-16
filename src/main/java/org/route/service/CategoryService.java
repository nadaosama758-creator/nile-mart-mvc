package org.route.service; // تأكدي من حالة الأحرف لاسم الباكيج

import org.route.repository.CategoryRepository;
import org.route.model.Category;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .collect(Collectors.toList());
    }

    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentSlug("").stream()
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .collect(Collectors.toList());
    }

    public List<Category> getSubCategories(String parentSlug) {
        return categoryRepository.findByParentSlug(parentSlug).stream()
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .collect(Collectors.toList());
    }

    public List<Category> getActiveSubCategories() {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getParentSlug() != null && !c.getParentSlug().isEmpty())
                .filter(c -> Boolean.TRUE.equals(c.getActive()))
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .collect(Collectors.toList());
    }

    public Optional<Category> getBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public void saveCategory(Category category) {
        validateCategory(category);
        categoryRepository.save(category);
    }

    private void validateCategory(Category category) {
        // 1. التحقق من صيغة الـ Slug (أحرف صغيرة، أرقام، وشرطات فقط بدون مسافات)
        if (category.getSlug() == null || !category.getSlug().matches("^[a-z0-9-]+$")) {
            throw new IllegalArgumentException("Invalid slug format. Use lowercase letters, digits, and hyphens only.");
        }

        // 2. التحقق من قاعدة ال parent يجب أن يكون Top-level category ولا يمكن أن يكون Sub-category
        String parentSlug = category.getParentSlug();
        if (parentSlug != null && !parentSlug.isEmpty()) {
            Category parent = categoryRepository.findBySlug(parentSlug)
                    .orElseThrow(() -> new IllegalArgumentException("Parent category does not exist."));

            if (parent.getParentSlug() != null && !parent.getParentSlug().isEmpty()) {
                throw new IllegalArgumentException("A sub-category cannot have its own children (Max 2 levels).");
            }
        }

        // 3.  (Unique within the same parent, ignoring case)
        String targetParent = (parentSlug == null) ? "" : parentSlug;
        boolean nameExists = categoryRepository.findByParentSlug(targetParent).stream()
                .anyMatch(c -> !c.getSlug().equals(category.getSlug()) &&
                        c.getName().equalsIgnoreCase(category.getName()));
        if (nameExists) {
            throw new IllegalArgumentException("Category name already exists under the same parent.");
        }
    }
}