# E-Commerce Backend

A RESTful e-commerce backend built with Java Spring Boot and MySQL.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA / Hibernate
- MySQL
- Lombok
- Maven

## Features
- User registration and login with BCrypt password encryption
- Product management (add, list, delete)
- Shopping cart (add items, view cart, remove items)
- Order placement and order history

## API Endpoints

### Users
- POST /api/users/register - Register new user
- POST /api/users/login - Login

### Products
- POST /api/products/add - Add product
- GET /api/products/all - Get all products
- GET /api/products/{id} - Get product by ID
- DELETE /api/products/{id} - Delete product

### Cart
- POST /api/cart/add - Add item to cart
- GET /api/cart/{userId} - View cart
- DELETE /api/cart/remove/{cartItemId} - Remove item from cart

### Orders
- POST /api/orders/place/{userId} - Place order
- GET /api/orders/history/{userId} - Get order history
- GET /api/orders/{orderId} - Get order by ID

## Setup
1. Clone the repository
2. Create MySQL database named `ecommerce`
3. Update database credentials in application.properties
4. Run the application