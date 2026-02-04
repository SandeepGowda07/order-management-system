# Register App - Spring Boot Magazine Store

A full-stack Spring Boot application for managing a magazine store, featuring user registration, product management, and order tracking.

## 🚀 Features

- **User System**: Secure registration and login using Spring Security.
- **Admin Dashboard**: Manage users, products, and view order statistics.
- **Product Management**: Browse, search, and view detailed magazine information.
- **Order System**: Place orders for magazines and track order status.
- **Security**: Role-based access control (ADMIN/USER), password hashing with BCrypt, and session management.
- **Responsive UI**: Modern, clean interface built with Thymeleaf and Bootstrap 5.

## 🛠️ Technology Stack

- **Backend**: Java 11, Spring Boot 2.4.3
- **Security**: Spring Security (JDBC Authentication)
- **Database**: MySQL
- **ORM**: Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf, Bootstrap 5, Bootstrap Icons
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 11 or higher
- MySQL Server
- Maven (or use the provided `./mvnw`)

## ⚙️ Configuration

1. **Database Setup**:
   Create a database named `my_database` in MySQL.
   ```sql
   CREATE DATABASE my_database;
   ```

2. **Application Properties**:
   Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## 🏃 How to Run

1. Clone or download the repository.
2. Navigate to the project root directory.
3. Build and run using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access the application at `http://localhost:8888`.

## 📁 Project Structure

- `src/main/java/com/register/springboot`:
  - `controller`: Web request handlers.
  - `model`: JPA entities (User, Product, Order).
  - `repository`: Data access interfaces.
  - `service`: Business logic for users, products, and orders.
  - `security`: Spring Security configuration and user details.
- `src/main/resources`:
  - `templates`: Thymeleaf HTML templates.
  - `application.properties`: Configuration file.

## 📝 License

This project is open-source and available for educational purposes.
