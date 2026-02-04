package com.register.springboot.controller;

import com.register.springboot.model.Product;
import com.register.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * ProductController - Handles product viewing for users
 * Users can browse and view magazine details
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Display list of all products/magazines
     * URL: GET /products
     */
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products"; // products.html
    }

    /**
     * View single product details
     * URL: GET /products/{id}
     */
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product-detail"; // product-detail.html
    }

    /**
     * Search products by name
     * URL: GET /products/search?name=xyz
     */
    @GetMapping("/search")
    public String searchProducts(@RequestParam String name, Model model) {
        model.addAttribute("products", productService.searchProducts(name));
        model.addAttribute("searchQuery", name);
        return "products";
    }
}
