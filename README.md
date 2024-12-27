# Loan Management API

This document provides an overview of the Loan Management API, including instructions on how to build, run, and use the application.

## Overview
The Loan Management API allows users to:
- Manage loans and installments.
- Pay loan installments.
- View loan details, including payment status and history.

## Requirements
Before building and running the project, ensure you have the following installed:
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.8.0 or higher
- A compatible relational database (e.g., PostgreSQL, MySQL)
- Docker (optional for containerized deployment)

## Build and Run Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/caglacolak/loan-management-api.git
cd loan-management-api
```

### 2. Configure the Application

#### Application Properties
Edit the `application.yml` file located in the `src/main/resources` directory to configure the database and other environment-specific settings. Example:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/loan_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  security:
    user:
      name: admin
      password: admin
```

### 3. Build the Project
Run the following command to build the project:
```bash
mvn clean install
```

### 4. Run the Application
Start the application using the following command:
```bash
mvn spring-boot:run
```
The application will be available at `http://localhost:8080`.

## Endpoints

### Authorization
All endpoints require Basic Authentication. Use the following credentials (configurable in `application.yml`):
- Username: `admin`
- Password: `admin`

### API Endpoints

#### 1. Loan Management
- **Create a Loan**
    - Endpoint: `POST /api/loans/create`
    - Request Body:
      ```json
      {
        "customerId": 1,
        "loanAmount": 1000,
        "isPaid": false,
        "interestRate": 0.1,
        "numberOfInstallments": 3
      }
      ```

- **Get Loan Details**
    - Endpoint: `GET /api/loans/{loanId}`

#### 2. Loan Installments
- **Pay Loan**
    - Endpoint: `POST /api/loans/pay/{loanId}?amount={amount}`

- **View Installments**
    - Endpoint: `GET /api/loans/installments/{loanId}`

#### 3. Customer Management
- **Create a Customer**
    - Endpoint: `POST /api/customers/create`
    - Request Body:
      ```json
      {
        "name": "Çağla",
        "surname": "Şimşek",
        "creditLimit": 100.0,
        "usedCreditLimit": 1.0
      }
      ```

- **Update Customer Credit Limit**
    - Endpoint: `POST /api/customers/update/{customerID}/creditLimit?newCreditLimit={newCreditLimit}`

- **Update Customer Used Credit Limit**
    - Endpoint: `POST /api/customers/update/{customerID}/usedCreditLimit?newUsedCreditLimit={newUsedCreditLimit}`

- **List Customer Details By Customer ID**
    - Endpoint: `GET /api/customers/{customerId}`

- **List All Customers**
    - Endpoint: `GET /api/customers/`

## Running Tests
To run unit tests, execute:
```bash
mvn test
```

## Docker Support

### Build Docker Image
Run the following command to build a Docker image for the application:
```bash
docker build -t loan-management-api .
```

### Run the Application in Docker
```bash
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/loan_db -e SPRING_DATASOURCE_USERNAME=your_username -e SPRING_DATASOURCE_PASSWORD=your_password loan-management-api
```

## Troubleshooting
- Ensure the database is running and the credentials are correct.
- Check logs for errors using:
  ```bash
  mvn spring-boot:run
  ```
- If tests fail due to mocking issues, ensure the correct Mockito version is included in `pom.xml`.

## Future Improvements
- Add Swagger documentation for easier API exploration.
- Implement rate limiting and improved error handling.
- Implement all tests

---

