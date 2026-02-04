package com.register.springboot.repository;

import com.register.springboot.model.Order;
import com.register.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * OrderRepository - Database operations for Order entity
 * Spring Data JPA automatically implements these methods
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user
    List<Order> findByUser(User user);

    // Find orders by user, ordered by date (newest first)
    List<Order> findByUserOrderByOrderDateDesc(User user);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Count orders by status
    long countByStatus(String status);
}
