# CASTEX

![Project Logo](logo.png)

Welcome to the README file for the eCommerce Website CASTEX! This project aims to create a dynamic and user-friendly eCommerce website similar to Amazon, allowing users to browse products, add them to their cart, and make purchases. The project is built using a combination of JavaScript, CSS, Java, and Spring for handling REST requests. Additionally, the project implements RSA encryption to ensure secure communication and data protection.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)
- [Contact Information](#contact-information)

## Features

- User registration and authentication
- Browse products by categories
- Search products by name, category, etc.
- Add products to the shopping cart
- Proceed to checkout and complete purchases
- User profile management
- Admin panel for product and user management
- RSA encryption for secure communication

## Technologies Used

- Frontend: HTML, CSS, JavaScript
- Backend: Java, Spring Framework (Spring Boot, Spring MVC)
- Encryption: RSA Encryption
- Database: in-memory 
- Version Control: GitHub

## Getting Started

### Prerequisites

Before you begin, make sure you have the following installed on your system:

- Java Development Kit (JDK)

## Installation

To get the eCommerce website project up and running on your local machine, follow these steps:

1. **Clone the repository:**

2. **Build and run the Spring Boot backend:**

    - Run the following command to start the Spring Boot application:

        ```bash
        ./mvnw spring-boot:run
        ```

    - The application is configured to use the H2 in-memory database by default. You can access the H2 console to manage the database by navigating to [http://localhost:8080/h2-console](http://localhost:8080/h2-console). Use the JDBC URL `jdbc:h2:mem:testdb`, and the username and password from your `application.properties` file.


3. **Access the website:**

    - Open your web browser and navigate to [http://localhost:8080](http://localhost:8080).

You have now successfully installed and set up the eCommerce website project on your local machine using the H2 in-memory database. You can explore the website, test its features, and make any necessary modifications to suit your requirements.

