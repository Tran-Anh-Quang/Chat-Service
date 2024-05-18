# Chat-App

## Overview

Chat-App is a Spring Boot-based web application that enables real-time chat functionality using WebSockets. This application leverages a variety of Spring Boot starters for web, data persistence, validation, and WebSocket communication, along with PostgreSQL as the database and Redis for real-time data handling.

## Project Structure

The project is a Maven-based application structured with the following key components:

- **Spring Boot Starters**: Various starters for rapid setup.
- **Database**: PostgreSQL for persistent storage.
- **Cache**: Redis for real-time messaging.
- **WebSockets**: For real-time chat functionality.

## Prerequisites

To build and run this application, you need the following:

- Java 17
- Maven 3.6.0 or higher
- PostgreSQL database
- Redis server

## Dependencies

The main dependencies used in this project are:

- **Spring Boot Starters**:
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-web`
  - `spring-boot-starter-websocket`
  - `spring-boot-starter-data-redis`
  - `spring-boot-starter-test` (for testing)
- **Database Driver**:
  - `org.postgresql:postgresql`
- **Lombok**: For boilerplate code reduction (optional).

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/Chat-App.git
   cd Chat-App
Configure the database:
Update the application.properties or application.yml file in src/main/resources with your PostgreSQL and Redis configurations.

Build the project:

- bash
- Copy code
- mvn clean install
- Run the application:

bash
Copy code
mvn spring-boot:run
Configuration
Configure the application properties in src/main/resources/application.properties:

properties
Copy code
# PostgreSQL configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/chat_app
spring.datasource.username=your_username
spring.datasource.password=your_password

# Redis configuration
spring.redis.host=localhost
spring.redis.port=6379

# Other configurations...
Usage
After running the application, you can access the chat functionality via the configured endpoints. The application supports basic CRUD operations for user management and real-time chat capabilities through WebSocket connections.
