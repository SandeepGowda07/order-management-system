package com.register.springboot.repository;

import com.register.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ProductRepository - Database operations for Product entity
 * Spring Data JPA automatically implements these methods
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products containing name (case-insensitive search)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find products with price less than specified amount
    List<Product> findByPriceLessThan(Double price);

    // Find products that are in stock
    List<Product> findByStockGreaterThan(Integer stock);
}
