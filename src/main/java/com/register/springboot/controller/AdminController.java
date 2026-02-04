package com.register.springboot.controller;

import com.register.springboot.model.Product;
import com.register.springboot.model.User;
import com.register.springboot.repository.UserRepository;
import com.register.springboot.service.OrderService;
import com.register.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AdminController - Handles admin operations
 * Admin can manage users, products, and orders
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    // ========================================
    // DASHBOARD
    // ========================================

    /**
     * Admin dashboard with statistics
     * URL: GET /admin/dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalProducts", productService.getProductCount());
        model.addAttribute("totalOrders", orderService.getOrderCount());
        return "admin/dashboard";
    }

    // ========================================
    // PRODUCT MANAGEMENT
    // ========================================

    /**
     * List all products for management
     * URL: GET /admin/products
     */
    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    /**
     * Show add product form
     * URL: GET /admin/products/add
     */
    @GetMapping("/products/add")
    public String showAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    /**
     * Process add product
     * URL: POST /admin/products/add
     */
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product,
            RedirectAttributes redirectAttributes) {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("success", "Product added successfully!");
        return "redirect:/admin/products";
    }

    /**
     * Show edit product form
     * URL: GET /admin/products/edit/{id}
     */
    @GetMapping("/products/edit/{id}")
    public String showEditProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "admin/add-product";
    }

    /**
     * Process edit product
     * URL: POST /admin/products/edit/{id}
     */
    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id,
            @ModelAttribute Product product,
            RedirectAttributes redirectAttributes) {
        product.setId(id);
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        return "redirect:/admin/products";
    }

    /**
     * Delete product
     * URL: GET /admin/products/delete/{id}
     */
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("success", "Product deleted!");
        return "redirect:/admin/products";
    }

    // ========================================
    // USER MANAGEMENT
    // ========================================

    /**
     * List all users
     * URL: GET /admin/users
     */
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    /**
     * Delete user
     * URL: GET /admin/users/delete/{id}
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id,
            RedirectAttributes redirectAttributes) {
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "User deleted!");
        return "redirect:/admin/users";
    }

    // ========================================
    // ORDER MANAGEMENT
    // ========================================

    /**
     * List all orders
     * URL: GET /admin/orders
     */
    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    /**
     * Update order status
     * URL: POST /admin/orders/{id}/status
     */
    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Order status updated!");
        return "redirect:/admin/orders";
    }
}
