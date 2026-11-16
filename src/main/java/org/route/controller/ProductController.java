package org.route.Controller; // تأكدي من اسم الباكيج عندك

import jakarta.validation.Valid;
import org.route.service.BrandService;
import org.route.service.CategoryService;
import org.route.service.ProductService;
import org.route.model.Brand;
import org.route.model.Category;
import org.route.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping("/product")
    public String getProducts(Model model) {
        List<Product> products = productService.getAvailableProducts();

        // خرائط لتحويل الـ slug إلى الاسم الحقيقي للعرض في الجدول
        Map<String, String> categoryNames = categoryService.getAllCategories().stream()
                .collect(Collectors.toMap(Category::getSlug, Category::getName));

        Map<String, String> brandNames = brandService.getAllBrands().stream()
                .collect(Collectors.toMap(Brand::getSlug, Brand::getName));

        model.addAttribute("availableProducts", products);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("brandNames", brandNames);

        return "products";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        populateDropdowns(model);
        return "productForm";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result,
                             Model model) {
        if (productService.isProductExist(product.getSku())) {
            result.rejectValue("sku", "400", "sku is already exist");
        }

        if (result.hasErrors()) {
            populateDropdowns(model);
            return "productForm";
        }
        try {
            productService.saveProduct(product);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            populateDropdowns(model);
            return "productForm";
        }

        return "redirect:/product";
    }

    private void populateDropdowns(Model model) {
        model.addAttribute("subCategories", categoryService.getActiveSubCategories());
        model.addAttribute("activeBrands", brandService.getActiveBrands());
    }
}