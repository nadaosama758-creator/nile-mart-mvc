package org.route.service;

import Exceptions.StoreException;
import lombok.RequiredArgsConstructor;
import org.route.model.Brand;
import org.route.model.Category;
import org.route.model.Product;
import org.route.repository.BrandRepository;
import org.route.repository.CategoryRepository;
import org.route.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service

public class ProductService {


    private final PricingService pricing;
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService  categoryService;

    @Autowired
    public ProductService(PricingService pricing, ProductRepository productRepository,
                          BrandService brandService, CategoryService categoryService) {
        this.pricing = pricing;
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }




    public  BigDecimal getPrice(Product product, int quantity){
        return pricing.getPrice(product, quantity);
    }

    public void reduceStock(String sku , int quantity){
        Product product= productRepository.findBySku(sku)
                .orElseThrow(() -> new StoreException("Product not found: " + sku));

        if (product.getStockQuantity() < quantity) {
            throw new StoreException("Stock is too low for product: " + sku);
        }
        product.setStockQuantity(product.getStockQuantity()-quantity);
        productRepository.save(product);
    }

    public List<Product> getAvailableProducts(){
        List<Product> availableProducts = new ArrayList<>();
        for(Product p : productRepository.findAll()){
            if (p.getStockQuantity() > 0) {
                availableProducts.add(p);
            }
        }
        return availableProducts;
        }

    private void validateProduct(Product product) {

        // 1.check (Sub-category & Active)
        if (product.getCategorySlug() == null || product.getCategorySlug().isEmpty()) {
            throw new StoreException("Product must belong to a sub-category.");
        }

        Category category = categoryService.getBySlug(product.getCategorySlug())
                .orElseThrow(() -> new StoreException("Category not found: " + product.getCategorySlug()));

        if (category.getParentSlug() == null || category.getParentSlug().isEmpty()) {
            throw new StoreException("Products can only be assigned to sub-categories, not top-level categories.");
        }

        if (!Boolean.TRUE.equals(category.getActive())) {
            throw new StoreException("Cannot assign product to an inactive category.");
        }

        // 2.Brand verification (optional; however, if present, it must be active)

        if (product.getBrandSlug() != null && !product.getBrandSlug().isEmpty()) {
            Brand brand = brandService.getBySlug(product.getBrandSlug())
                    .orElseThrow(() -> new StoreException("Brand not found: " + product.getBrandSlug()));

            if (!brand.isActive()) {
                throw new StoreException("Cannot assign product to an inactive brand.");
            }
        }
    }

    public void saveProduct(Product product) {
        validateProduct(product);
        productRepository.save(product);
    }

    public boolean isProductExist(String sku) {
        return productRepository.findBySku(sku).isPresent();
    }



}
