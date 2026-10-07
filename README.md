# RoopBank – Banking Application

RoopBank is a **modular monolithic banking application** built with Java and Spring Boot.

The application provides REST APIs for account, card, loan, transaction, authentication, and notification management.

The project demonstrates **JWT-based authentication, role-based authorization, MySQL persistence, Kafka-based transaction notifications, Docker containerization, request validation, global exception handling, JPA auditing, pagination, sorting, testing, and OpenAPI/Swagger documentation**.

---

## Features

* Customer and Account Management
* Card Management
* Loan Management
* Loan Payment Management
* Banking Transactions
* JWT-based Authentication
* Role-based Authorization
* Transaction Notifications using Apache Kafka
* Global Exception Handling
* Request Validation
* JPA Auditing
* Pagination and Sorting
* OpenAPI / Swagger API Documentation
* Unit and Repository Testing
* Docker and Docker Compose Support

---

## Tech Stack

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java 21           | Programming Language           |
| Spring Boot       | Backend Framework              |
| Spring Data JPA   | Database Access                |
| Spring Security   | Authentication & Authorization |
| JWT               | Stateless Authentication       |
| MySQL 8.4         | Relational Database            |
| Apache Kafka      | Event-driven Notifications     |
| Maven             | Build & Dependency Management  |
| Docker            | Containerization               |
| Docker Compose    | Multi-container Environment    |
| OpenAPI / Swagger | API Documentation              |
| JUnit             | Testing                        |

---

## Architecture

RoopBank follows a **modular monolithic architecture**.

All business modules run inside a single Spring Boot application, while each module has its own responsibilities and package structure.

```text
                         RoopBank
                            |
              Spring Boot Application
                            |
       ------------------------------------------------
       |          |          |          |             |
    Account      Card       Loan    Transaction      Auth
       |          |          |          |             |
       |          |          |          |             |
       ------------------ MySQL ----------------------
                            |
                       Transaction Event
                            |
                          Kafka
                            |
                   Notification Module
                            |
                     SMS Notification
```

The application is currently a **monolith**, not a microservices architecture.

---

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── rooptech/
│   │           └── bankingapp/
│   │               ├── account/
│   │               ├── auth/
│   │               ├── card/
│   │               ├── common/
│   │               ├── config/
│   │               ├── loan/
│   │               ├── notification/
│   │               └── transaction/
│   │
│   └── resources/
│       ├── application.yaml
│       └── data.sql
│
└── test/
    └── java/
        └── com/
            └── rooptech/
                └── bankingapp/
```

---

# Modules

## 1. Account Module

Responsible for customer and bank account management.

Main responsibilities:

* Create customer and linked account
* Retrieve customer/account information
* Update customer information
* Delete customer/account information
* Account number generation
* Account validation

---

## 2. Authentication Module

Responsible for application authentication and authorization.

Main responsibilities:

* User login
* JWT generation
* JWT validation
* Spring Security integration
* Role-based authorization
* User details management

---

## 3. Card Module

Responsible for managing customer cards.

Main responsibilities:

* Create cards
* Retrieve card information
* Update card information
* Card status management
* Card validation

---

## 4. Loan Module

Responsible for loan and loan payment management.

Main responsibilities:

* Loan management
* Loan product management
* Loan EMI calculation
* Loan payment management
* Outstanding amount tracking
* Payment status management

---

## 5. Transaction Module

Responsible for banking transactions.

Main responsibilities:

* Process transactions
* Maintain transaction records
* Transaction status management
* Transaction type management
* Pagination and sorting
* Publish transaction events to Kafka

---

## 6. Notification Module

Responsible for processing transaction notification events.

The module uses Apache Kafka for event-driven communication.

Transaction flow:

```text
Transaction Service
        |
        ↓
TransactionEventProducer
        |
        ↓
Kafka Topic
transaction-events
        |
        ↓
TransactionNotificationConsumer
        |
        ↓
NotificationService
        |
        ↓
SmsNotificationService
```

This allows transaction processing and notification processing to be handled separately within the application.

---

# Database

RoopBank uses **MySQL 8.4** as its relational database.

The application uses:

* Spring Data JPA
* Hibernate
* JPA Auditing
* Entity relationships
* Database initialization using `data.sql`

Database configuration for Docker is handled through environment variables.

---

# Kafka

Apache Kafka is used for transaction notification events.

### Topic

```text
transaction-events
```

### Consumer Group

```text
notification-group
```

### Event Flow

```text
Banking Transaction
        ↓
TransactionEventProducer
        ↓
transaction-events
        ↓
TransactionNotificationConsumer
        ↓
NotificationService
```

The transaction event contains information required by the notification module to process a transaction notification.

---

# API Documentation

RoopBank uses **OpenAPI / Swagger UI** for interactive API documentation.

When the application is running locally:

```text
http://localhost:8080/swagger-ui/index.html
```

Open the URL in a browser to explore and test the available REST APIs.

> `localhost` refers to the machine where the RoopBank application is running. For local development, this will normally be the developer's own machine.

---

# Running the Application

## Prerequisites

For the Docker-based setup, install:

* Git
* Docker Desktop

Docker Desktop provides Docker Engine and Docker Compose.

---

## Clone the Repository

```bash
git clone https://github.com/roop-bose/roopbank.git
```

Move into the project directory:

```bash
cd roopbank
```

---

# Environment Variables

The application uses environment variables for sensitive configuration such as database passwords and the JWT secret.

Create a `.env` file in the project root.

Example:

```env
DB_USERNAME=roopbank
DB_PASSWORD=your_database_password
MYSQL_ROOT_PASSWORD=your_mysql_root_password
JWT_SECRET=your_secure_jwt_secret
```

Do not commit the `.env` file to Git.

The repository already contains `.env` in `.gitignore`.

---

# Run with Docker Compose

From the project root, first build the application JAR:

```powershell
.\mvnw.cmd clean package
```

Then build the Docker image:

```bash
docker build -t roopbank-app:latest .
```

Start the application and supporting services:

```bash
docker compose up -d
```

This starts the required containers:

```text
RoopBank
   |
   ├── MySQL
   ├── Kafka
   └── Spring Boot Application
```

Check running containers:

```bash
docker ps
```

Stop the application:

```bash
docker compose down
```

---

# Application URLs

When the Docker Compose environment is running:

### Application

```text
http://localhost:8080
```

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### MySQL

```text
localhost:3307
```

### Kafka

```text
localhost:9093
```

The MySQL and Kafka ports are exposed for local development.

---

# Testing

The project contains tests using JUnit and Spring Boot testing support.

Current test coverage includes areas such as:

* Account Controller
* Account Repository
* Customer Repository
* Account Service
* Spring Boot application context

Run the Maven test suite using:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

---

# Build the Application

To build the application using Maven:

### Windows

```powershell
.\mvnw.cmd clean package
```

The generated JAR file will be available under:

```text
target/
```

Example:

```text
target/banking-app-0.0.1-SNAPSHOT.jar
```

---

# Docker Image

The project includes a `Dockerfile` for building the Spring Boot application image.

Build the JAR first:

```powershell
.\mvnw.cmd clean package
```

Then build the Docker image:

```bash
docker build -t roopbank-app:latest .
```

Docker Compose uses this image to run the application container.

---

# Git Workflow

The project is maintained using Git and GitHub.

Typical development workflow:

```text
Make Changes
     ↓
git status
     ↓
git add .
     ↓
git commit -m "Description of changes"
     ↓
git push
```

The GitHub repository:

```text
https://github.com/roop-bose/roopbank
```

---

# Security

Sensitive configuration values are not stored directly in the source code.

The project uses environment variables for:

* Database password
* MySQL root password
* JWT secret

The `.env` file is excluded from Git using `.gitignore`.

Never commit real passwords, tokens, API keys, or other secrets to the repository.

---

# Future Improvements

Planned improvements include:

* GitHub Actions CI/CD pipeline
* Automated Docker image publishing
* Cloud deployment
* Expanded automated test coverage
* Production-ready observability
* Further architectural evolution where appropriate

---

# Author

**Roopa Ram Bose**

Java Backend Developer

GitHub:

https://github.com/roop-bose

---

## License

This project is currently maintained as a personal portfolio and learning project.
