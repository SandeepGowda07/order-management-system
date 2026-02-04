package com.register.springboot.service;

import com.register.springboot.model.Product;
import com.register.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * ProductService - Business logic for Product operations
 * Handles CRUD operations for magazines
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all products from database
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get product by ID
     */
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    /**
     * Save product (create new or update existing)
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Delete product by ID
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Search products by name
     */
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Get count of all products
     */
    public long getProductCount() {
        return productRepository.count();
    }
}
