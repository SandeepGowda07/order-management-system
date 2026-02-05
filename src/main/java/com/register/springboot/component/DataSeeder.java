package com.register.springboot.component;

import com.register.springboot.model.Product;
import com.register.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSeeder - Populates the database with initial magazine data if empty.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private com.register.springboot.repository.UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            if (userRepository.countByRolesContaining("ROLE_ADMIN") == 0) {
                seedAdminUser();
            }
        } catch (Exception e) {
            System.err.println("Failed to seed admin user: " + e.getMessage());
        }

        try {
            if (productRepository.count() == 0) {
                seedProducts();
            }
        } catch (Exception e) {
            System.err.println("Failed to seed products: " + e.getMessage());
        }
    }

    private void seedAdminUser() {
        try {
            // Extra check to see if "Sandeep" already exists
            if (userRepository.findByUserName("Sandeep") != null) {
                System.out.println("Admin 'Sandeep' already exists, skipping seeding.");
                return;
            }

            com.register.springboot.model.User admin = new com.register.springboot.model.User();
            admin.setUserName("Sandeep");
            admin.setPassword(passwordEncoder.encode("Sandy123")); // Default password
            admin.setEmail("sandeep@example.com");
            admin.setAge(25);
            admin.setDob("2000-01-01");
            admin.setRoles("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("Database Seeded: Admin User 'Sandeep' Added!");
        } catch (Exception e) {
            System.err.println("Error saving admin user: " + e.getMessage());
        }
    }

    private void seedProducts() {
        try {
            List<Product> magazines = new ArrayList<>();

            magazines.add(createProduct("National Geographic",
                    "Explore the world through world-class photojournalism and storytelling.", 12.99, 50,
                    "2024-01-01"));
            magazines.add(
                    createProduct("Time", "The global magazine of news, analysis, and ideas.", 9.99, 100,
                            "2024-02-15"));
            magazines
                    .add(createProduct("Vogue", "The world's most influential fashion magazine.", 14.50, 75,
                            "2024-03-01"));
            magazines
                    .add(createProduct("The New Yorker", "In-depth reporting, cultural commentary, fiction, and humor.",
                            8.99, 120, "2024-04-10"));
            magazines.add(
                    createProduct("Forbes",
                            "Business, investing, technology, entrepreneurship, leadership, and lifestyle.",
                            11.00, 60, "2024-01-20"));
            magazines.add(createProduct("Scientific American",
                    "The most trustworthy source for news about science and technology.", 15.00, 40, "2023-12-05"));
            magazines.add(createProduct("Wired",
                    "Where tomorrow is realized. Covering business, lifestyle, and innovation.", 7.99, 85,
                    "2024-02-28"));
            magazines.add(createProduct("Rolling Stone",
                    "The legendary magazine covering music, politics, and pop culture.", 10.50, 90, "2024-03-15"));
            magazines.add(createProduct("The Economist",
                    "Authoritative insight and opinion on international news, politics, and business.", 18.00, 55,
                    "2024-04-01"));
            magazines.add(createProduct("Cosmopolitan",
                    "The ultimate guide for fashion, beauty, relationships, and health.", 6.99, 150, "2024-01-12"));
            magazines.add(
                    createProduct("Fast Company", "Focusing on technology, business, and design.", 9.00, 70,
                            "2024-03-22"));
            magazines.add(createProduct("Better Homes & Gardens", "Inspiration for your home and lifestyle.", 5.99, 200,
                    "2023-11-30"));
            magazines.add(
                    createProduct("People", "Celebrity news, human interest stories, and royal family updates.", 4.99,
                            300, "2024-04-05"));
            magazines.add(createProduct("Harvard Business Review",
                    "Ideas and advice on strategy, leadership, and management.", 20.00, 30, "2024-01-05"));
            magazines.add(createProduct("Architectural Digest",
                    "The international design authority, featuring luxury homes and design.", 13.99, 45, "2024-02-10"));
            magazines
                    .add(createProduct("Men's Health", "The leading authority on men's fitness, health, and lifestyle.",
                            7.50, 110, "2024-03-05"));
            magazines.add(createProduct("Smithsonian Magazine", "Deep dives into history, science, art, and culture.",
                    11.99, 65, "2024-01-18"));
            magazines
                    .add(createProduct("Esquire", "Style, fashion, politics, and culture for the modern man.", 8.50, 95,
                            "2024-02-20"));
            magazines.add(
                    createProduct("Food & Wine", "The definitive guide to culinary adventure and entertaining.", 6.25,
                            130, "2024-03-12"));
            magazines.add(createProduct("GQ", "Covering style, grooming, fitness, and more.", 7.99, 105, "2024-04-02"));

            productRepository.saveAll(magazines);
            System.out.println("Database Seeded: 20 Magazines Added!");
        } catch (Exception e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    private Product createProduct(String name, String desc, Double price, Integer stock, String date) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setStock(stock);
        p.setPublishDate(LocalDate.parse(date));
        return p;
    }
}
