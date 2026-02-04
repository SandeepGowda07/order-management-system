package com.register.springboot.controller;

import com.register.springboot.model.Order;
import com.register.springboot.model.Product;
import com.register.springboot.model.User;
import com.register.springboot.repository.UserRepository;
import com.register.springboot.service.OrderService;
import com.register.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * OrderController - Handles order placement and viewing
 * Users can place orders and view their order history
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Show order form for a product
     * URL: GET /order/{productId}
     */
    @GetMapping("/order/{productId}")
    public String showOrderForm(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("order", new Order());
        return "order-form"; // order-form.html
    }

    /**
     * Process order submission
     * URL: POST /order/{productId}
     */
    @PostMapping("/order/{productId}")
    public String placeOrder(@PathVariable Long productId,
            @ModelAttribute Order order,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        // Get current logged-in user
        String username = authentication.getName();
        User user = userRepository.findByUserName(username);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found. Please login again.");
            return "redirect:/login";
        }

        // Validate order before placing
        String errors = orderService.validateOrder(order);
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:/order/" + productId;
        }

        // Place order
        Order savedOrder = orderService.placeOrder(order, user, productId);

        if (savedOrder == null) {
            redirectAttributes.addFlashAttribute("error", "Product not found.");
            return "redirect:/products";
        }

        if ("ACCEPTED".equals(savedOrder.getStatus())) {
            redirectAttributes.addFlashAttribute("success",
                    "Order placed successfully! Order ID: " + savedOrder.getId());
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Order rejected due to invalid details.");
        }

        return "redirect:/my-orders";
    }

    /**
     * View user's order history
     * URL: GET /my-orders
     */
    @GetMapping("/my-orders")
    public String myOrders(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUserName(username);

        if (user != null) {
            model.addAttribute("orders", orderService.getOrdersByUser(user));
        }
        return "my-orders"; // my-orders.html
    }
}
