# RoopBank – Banking Application

![RoopBank CI](https://github.com/roop-bose/roopbank/actions/workflows/ci.yml/badge.svg?branch=main)

RoopBank is a **modular monolithic banking application** built using Java 21 and Spring Boot.

The application provides REST APIs for account, card, loan, transaction, authentication, and notification management.

This project demonstrates backend development concepts including **JWT-based authentication, role-based authorization, MySQL persistence, Kafka-based transaction notifications, Docker containerization, request validation, global exception handling, JPA auditing, pagination, sorting, automated testing, and OpenAPI/Swagger documentation**.

The project also uses **GitHub Actions for Continuous Integration (CI)** to automatically build the application and run tests.

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
* Automated Testing with JUnit and Spring Boot Test
* GitHub Actions Continuous Integration (CI)
* Docker and Docker Compose Support

---

## Tech Stack

| Technology        | Purpose                          |
| ----------------- | -------------------------------- |
| Java 21           | Programming Language             |
| Spring Boot       | Backend Framework                |
| Spring Data JPA   | Database Access                  |
| Spring Security   | Authentication and Authorization |
| JWT               | Stateless Authentication         |
| MySQL 8.4         | Relational Database              |
| Apache Kafka      | Event-driven Notifications       |
| Maven             | Build and Dependency Management  |
| Docker            | Containerization                 |
| Docker Compose    | Multi-container Environment      |
| Git and GitHub    | Version Control                  |
| GitHub Actions    | Continuous Integration           |
| OpenAPI / Swagger | API Documentation                |
| JUnit             | Automated Testing                |

---

## Architecture

RoopBank currently follows a **modular monolithic architecture**.

All business modules run inside a single Spring Boot application. Each module has its own responsibilities and package structure.

```text
                       RoopBank
                           |
                Spring Boot Application
                           |
       -----------------------------------------
       |          |         |         |        |
    Account      Card      Loan   Transaction  Auth
       |          |         |         |
       ---------------------------------
                           |
                         MySQL

                   Transaction Events
                           |
                         Kafka
                           |
                  Notification Module
                           |
                 SMS Notification Service
```

**Architecture note:** RoopBank is currently a modular monolith, not a microservices application. The modules are organized to support future architectural evolution.

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

## Modules

### 1. Account Module

Responsible for customer and bank account management.

* Create customers and linked bank accounts
* Retrieve customer and account information
* Update customer information
* Delete customer and account information
* Generate account numbers
* Validate account-related requests

### 2. Authentication Module

Responsible for application authentication and authorization.

* User login
* JWT generation and validation
* Spring Security integration
* Role-based authorization
* User details management

### 3. Card Module

Responsible for managing customer cards.

* Create cards
* Retrieve card information
* Update card information
* Manage card status
* Validate card-related requests

### 4. Loan Module

Responsible for loan and loan payment management.

* Loan management
* Loan product management
* EMI calculation
* Loan payment management
* Outstanding amount tracking
* Payment status management

### 5. Transaction Module

Responsible for banking transactions.

* Process banking transactions
* Maintain transaction records
* Manage transaction types and statuses
* Support pagination and sorting
* Publish transaction events to Kafka

### 6. Notification Module

Responsible for processing transaction notification events using Apache Kafka.

**Event flow:**

```text
Transaction Service
        |
        v
TransactionEventProducer
        |
        v
Kafka Topic
transaction-events
        |
        v
TransactionNotificationConsumer
        |
        v
NotificationService
        |
        v
SmsNotificationService
```

This event-driven approach separates notification processing from the main transaction flow within the application. The notification module is currently part of the same Spring Boot application.

---

## Database

RoopBank uses **MySQL 8.4** as its relational database.

Database technologies and capabilities include:

* Spring Data JPA
* Hibernate
* JPA Auditing
* Entity relationships
* Database initialization using `data.sql`

Database credentials and other sensitive configuration are supplied through environment variables.

---

## Apache Kafka

Apache Kafka is used for transaction notification events.

**Topic**

```text
transaction-events
```

**Consumer Group**

```text
notification-group
```

**Event flow**

```text
Banking Transaction
        |
        v
TransactionEventProducer
        |
        v
transaction-events
        |
        v
TransactionNotificationConsumer
        |
        v
NotificationService
```

The producer publishes transaction events, and the consumer processes them for notification handling.

---

## API Documentation

RoopBank uses **OpenAPI / Swagger UI** for interactive API documentation.

When the application is running locally, open:

http://localhost:8080/swagger-ui/index.html

Swagger UI allows developers to explore the documented REST endpoints and try supported API requests.

---

## Continuous Integration – GitHub Actions

RoopBank uses **GitHub Actions** to automate the build and test process.

The CI workflow runs when configured GitHub events, such as pushes and pull requests, trigger it.

The workflow helps verify that code changes can be built and tested automatically.

* Maven build and automated test execution
* Automated validation of code changes
* Test results available in GitHub Actions

**Latest verified test result:** 41 tests passed, with 0 failures and 0 errors.

[View GitHub Actions workflow runs](https://github.com/roop-bose/roopbank/actions)

---

## Running the Application

### Prerequisites

For the Docker-based setup, install:

* Git
* Docker Desktop

Docker Desktop provides Docker Engine and Docker Compose.

### Clone the Repository

```bash
git clone https://github.com/roop-bose/roopbank.git
cd roopbank
```

---

## Environment Variables

Create a `.env` file in the project root for the environment variables required by your Docker Compose configuration.

Example:

```env
DB_USERNAME=roopbank
DB_PASSWORD=your_database_password
MYSQL_ROOT_PASSWORD=your_mysql_root_password
JWT_SECRET=your_secure_jwt_secret
```

Use values that match the configuration expected by the application and `compose.yml`.

**Important:** The example values are placeholders. Do not commit real passwords, JWT secrets, or other sensitive values to Git.

---

## Run with Docker Compose

Run these commands from the project root.

**1. Build the Spring Boot application**

On Windows PowerShell:

```powershell
.\mvnw.cmd clean package
```

**2. Build the Docker image**

```bash
docker build -t roopbank-app:latest .
```

**3. Start the application and supporting services**

```bash
docker compose up -d
```

Docker Compose starts the configured services:

```text
RoopBank Environment
        |
        |-- Spring Boot Application
        |-- MySQL
        |-- Apache Kafka
```

**4. Check container status**

```bash
docker compose ps
```

**5. View application logs**

```bash
docker compose logs -f app
```

**6. Stop the environment**

```bash
docker compose down
```

---

## Application URLs

When the Docker Compose environment is running and the services have started successfully:

| Service                 | Local Address                               |
| ----------------------- | ------------------------------------------- |
| Spring Boot Application | http://localhost:8080                       |
| Swagger UI              | http://localhost:8080/swagger-ui/index.html |
| MySQL                   | `localhost:3307`                            |
| Kafka                   | `localhost:9093`                            |

The MySQL and Kafka addresses above are the host ports configured for local development. Container-to-container communication uses the service addresses and ports defined in `compose.yml`.

---

## Testing

RoopBank includes automated tests using JUnit and Spring Boot testing support.

Current test coverage includes:

* Account Controller
* Account Repository
* Customer Repository
* Account Service
* Spring Boot application context

**Latest verified result: 41 tests passed, with 0 failures and 0 errors.** This result describes the current test suite; it does not mean every module has comprehensive test coverage.

Run the tests with Maven:

**Windows PowerShell**

```powershell
.\mvnw.cmd test
```

**Linux / macOS**

```bash
./mvnw test
```

The GitHub Actions CI workflow also runs the configured build and tests.

---

## Build the Application

Build the application using Maven.

**Windows PowerShell**

```powershell
.\mvnw.cmd clean package
```

The generated JAR is available under:

```text
target/banking-app-0.0.1-SNAPSHOT.jar
```

---

## Docker Image

The project includes a `Dockerfile` for building a container image of the Spring Boot application.

Build the JAR first:

```powershell
.\mvnw.cmd clean package
```

Build the image:

```bash
docker build -t roopbank-app:latest .
```

Docker Compose uses the configured image to run the application container.

---

## Git Workflow

The project uses Git and GitHub for version control.

Typical development workflow:

```text
Make Changes
     |
     v
git status
     |
     v
git add .
     |
     v
git commit -m "Describe your changes"
     |
     v
git push
     |
     v
GitHub Actions CI
```

**Repository:** [github.com/roop-bose/roopbank](https://github.com/roop-bose/roopbank)

---

## Security

The project uses environment variables for sensitive configuration, including:

* Database username and password
* MySQL root password
* JWT secret

Keep real credentials out of source control. Ensure `.env` is ignored by Git and never commit passwords, tokens, API keys, or other secrets.

---

## Future Improvements

Planned improvements include:

* Refactor the modular monolith into a microservices architecture
* Define service boundaries for account, card, loan, and transaction domains
* Introduce an API Gateway and centralized service configuration
* Implement inter-service communication
* Automate Docker image publishing
* Explore cloud deployment
* Expand automated test coverage across all modules
* Add production-ready monitoring and observability

---

## Author

**Roopa Ram Bose**

Java Backend Developer

[GitHub Profile](https://github.com/roop-bose)

---

## License

This project is maintained as a personal portfolio and learning project.
