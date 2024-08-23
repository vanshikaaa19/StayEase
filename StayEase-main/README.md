# StayEase

## Problem Statement

Develop a RESTFul API service using Spring Boot to manage a hotel booking system with role-based access control and MySQL for data persistence.

## Features

- **Hotel Management**: Create, Update, Delete hotels (Admin and Manager roles).
- **Booking Management**: Create, Update, Delete bookings.
- **User Management**: Register, Update, Delete users.
- **Role-Based Access Control**:
    - GET endpoints: Accessible by Customer, Admin, and Manager roles.
    - POST and DELETE endpoints: Accessible by Admin role.
    - PUT endpoints: Accessible by Admin and Manager roles.

## Technologies Used

- **Spring Boot**: Facilitates rapid development and deployment of Java applications.
- **Spring Data JPA**: Simplifies database operations with ORM (Object-Relational Mapping) capabilities.
- **Spring Web**: Supports building web applications using the MVC (Model-View-Controller) pattern.
- **Spring Security**: Provides authentication and authorization capabilities.
- **MySQL**: Database management system used for persistent data storage.
- **Lombok**: Reduces boilerplate code by automatically generating getters, setters, constructors, etc.
- **Swagger UI**: Provides interactive documentation for RESTFul APIs.

## Setup Procedure

### Prerequisites

- JDK 11 or higher installed
- Maven build tool installed
- Docker installed (for MySQL database)

### Steps to Setup

1. Clone the repository
   ```bash
   git clone https://github.com/vanshikaaa19/StayEase.git
   cd StayEase
   ```
2. Start MySQL Database
   Ensure Docker is running, then execute:
   ```bash
   docker-compose up -d
   ```
3. Build and Run the Application
   ```bash
    ./gradlew clean build
    ./gradlew bootRun
   ```
4. Access Swagger UI
   Open a web browser and navigate to:
   ```bash
   http://localhost:8080/swagger-ui.html
   ```
   Swagger UI will display all available endpoints and allow you to interact with the API.
<!-- 5. Import Postman Collection

   [Problem Statement](./External_Resource/Week%204%20-%20Problem%20Statement_%20StayEase.pdf)

   [Postman-Collection-File](./External_Resource/StayEase.postman_collection.json)

   [Step-by-Step Procedure](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/) -->

### Usage

- Use Swagger UI to test and explore different endpoints.
- Perform CRUD operations on hotels, bookings, and users as needed.
- Integrate additional features or extend functionalities based on project requirements.

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Lombok Documentation](https://projectlombok.org/)
- [Swagger UI Documentation](https://swagger.io/tools/swagger-ui/)
