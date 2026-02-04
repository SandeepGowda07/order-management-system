package com.register.springboot.service;

import com.register.springboot.model.Order;
import com.register.springboot.model.Product;
import com.register.springboot.model.User;
import com.register.springboot.repository.OrderRepository;
import com.register.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

/**
 * OrderService - Business logic for Order operations
 * Handles order placement and validation (same rules as Assignment 5)
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // ========================================
    // VALIDATION PATTERNS (Same as Assignment 5)
    // ========================================

    // Name: Only letters and spaces allowed
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    // Phone: 10 digits, must start with 7, 8, or 9
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[789]\\d{9}$");

    // City: Only letters and spaces
    private static final Pattern CITY_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    // State: Only letters and spaces
    private static final Pattern STATE_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    // Card Number: Exactly 16 digits
    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{16}$");

    // ========================================
    // VALIDATE ORDER DETAILS
    // ========================================

    /**
     * Validates order details and returns error messages if any
     * 
     * @param order The order to validate
     * @return Empty string if valid, error messages if invalid
     */
    public String validateOrder(Order order) {
        StringBuilder errors = new StringBuilder();

        // Validate Name
        if (order.getCustomerName() == null ||
                !NAME_PATTERN.matcher(order.getCustomerName()).matches()) {
            errors.append("Name must contain only letters. ");
        }

        // Validate Phone
        if (order.getPhone() == null ||
                !PHONE_PATTERN.matcher(order.getPhone()).matches()) {
            errors.append("Phone must be 10 digits starting with 7, 8, or 9. ");
        }

        // Validate City
        if (order.getCity() == null ||
                !CITY_PATTERN.matcher(order.getCity()).matches()) {
            errors.append("City must contain only letters. ");
        }

        // Validate State
        if (order.getState() == null ||
                !STATE_PATTERN.matcher(order.getState()).matches()) {
            errors.append("State must contain only letters. ");
        }

        // Validate Card Number
        if (order.getCardNumber() == null ||
                !CARD_PATTERN.matcher(order.getCardNumber()).matches()) {
            errors.append("Card number must be exactly 16 digits. ");
        }

        return errors.toString();
    }

    // ========================================
    // PLACE ORDER
    // ========================================

    /**
     * Places an order after validation
     * 
     * @param order     Order details from form
     * @param user      Current logged-in user
     * @param productId ID of the product being ordered
     * @return Saved order with status (ACCEPTED or REJECTED)
     */
    public Order placeOrder(Order order, User user, Long productId) {
        // Get product from database
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }

        // Set relationships
        order.setUser(user);
        order.setProduct(product);

        // Calculate total price
        if (order.getQuantity() == null || order.getQuantity() < 1) {
            order.setQuantity(1);
        }
        order.setTotalPrice(product.getPrice() * order.getQuantity());

        // Validate order details
        String validationErrors = validateOrder(order);

        if (validationErrors.isEmpty()) {
            order.setStatus("ACCEPTED");
        } else {
            order.setStatus("REJECTED");
        }

        // Save and return
        return orderRepository.save(order);
    }

    // ========================================
    // GET ORDERS
    // ========================================

    /**
     * Get all orders for a specific user
     */
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    /**
     * Get all orders (for Admin)
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Get order by ID
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // ========================================
    // UPDATE ORDER STATUS (for Admin)
    // ========================================

    /**
     * Update order status (PENDING, ACCEPTED, REJECTED)
     */
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    /**
     * Get count of all orders
     */
    public long getOrderCount() {
        return orderRepository.count();
    }
}
