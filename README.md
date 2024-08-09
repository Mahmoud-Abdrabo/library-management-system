# 📚 Library Management System

Welcome to the **Library Management System**! This application is built using **Spring Boot** and provides a RESTful API for managing books, patrons, and borrowing records in a library.

## 📋 Table of Contents

- [Features](#-features)
- [Getting Started](#-getting-started)
- [Running the Application](#-running-the-application)
- [API Endpoints](#-api-endpoints)
- [Security Configuration](#-security-configuration)
- [Unit Testing](#-unit-testing)
- [Exception Handling](#-exception-handling)
- [Technology Stack](#-technology-stack)
- [License](#-license)

## ✨ Features

- **Book Management**: Add, update, and delete books.
- **Patron Management**: Add, update, and delete patrons.
- **Borrowing Records**: Track which patrons have borrowed which books, including borrow and return dates.
- **Security**: HTTP basic JDBC Authentication and role-based Authorization.
- **Global Exception Handling**: Structured error responses with detailed messages.

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 22**: The application is built and tested with Java 22.
- **Maven 3.9.7**: Used for dependency management and building the application.
- **MySQL**: The application uses MySQL as its relational database. Ensure you have a running MySQL instance.

### Installation

1. **Clone the Repository**

    ```bash
    git clone https://github.com/YourUsername/LibraryManagementSystem.git
    cd LibraryManagementSystem
    ```

2. **Configure the Database**

    Update the `application.properties` file located in `src/main/resources/` with your database details:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/library_management
    spring.datasource.username=yourUsername
    spring.datasource.password=yourPassword
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Install Dependencies**

    Use Maven to install all the required dependencies:

    ```bash
    mvn clean install
    ```

## 🏃 Running the Application

You can run the application in several ways:

### Using Maven

```bash
mvn spring-boot:run
```

### Running the Jar

If you prefer to run the built JAR file:

```bash
java -jar target/LibraryManagementSystem-0.0.1-SNAPSHOT.jar
```

### Accessing the Application

Once the application is running, access it at:

```bash
http://localhost:8080/
```

## 🌐 API Endpoints

Here is a summary of the available API endpoints:


### Book Endpoints

- Get All Books

```bash
GET /api/books
```

Response example :

```bash
[
  {
    "id": 1,
    "title": "Refactoring",
    "author": "Martin Fowler",
    "publicationYear": "1999",
    "isbn": "9780201485677"
  },
  {
    "id": 2,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "publicationYear": "2018",
    "isbn": "9780134685991"
  }
]
```

- Get Book by ID

```bash
GET /api/books/{id}
```

Response example :

```bash
{
  "id": 1,
  "title": "Refactoring",
  "author": "Martin Fowler",
  "publicationYear": "1999",
  "isbn": "9780201485677"
}
```

- Create a New Book

```bash
POST /api/books
```

Request Body: JSON representation of the new book.

- Update an existing Book

```bash
PUT /api/books/{id}
```

Request Body: JSON representation of the updated book.

- Delete a Book

```bash
DELETE /api/books/{id}
```

### Patrons Endpoints

- Get All Patrons

```bash
GET /api/patrons
```

Response example :

```bash
[
  {
    "id": 1,
    "name": "Alice Johnson",
    "contactInformation": "alice.johnson@example.com"
  },
  {
    "id": 2,
    "name": "Evan Drake",
    "contactInformation": "evan.drake@example.com"
  }
]
```

- Get Patron by ID

```bash
GET /api/patrons/{id}
```

Response example :

```bash
{
  "id": 1,
  "name": "Alice Johnson",
  "contactInformation": "alice.johnson@example.com"
}
```

- Create a New Patron

```bash
POST /api/patrons
```

Request Body: JSON representation of the new patron.

- Update an existing Patron

```bash
PUT /api/patrons/{id}
```

Request Body: JSON representation of the updated patron.

- Delete a Patron

```bash
DELETE /api/patrons/{id}
```

### Borrowing Records Endpoints

- Get All Borrowing Records

```bash
GET /api/records
```

Response example :

```bash
[
  {
    "id": 1,
    "borrowDate": "2024-08-08",
    "returnDate": "2024-08-16",
    "book": {
      "id": 1,
      "title": "Refactoring",
      "author": "Martin Fowler",
      "publicationYear": "1999",
      "isbn": "9780201485677"
    },
    "patron": {
      "id": 1,
      "name": "Alice Johnson",
      "contactInformation": "alice.johnson@example.com"
    }
  },
  {
  "id": 2,
  "borrowDate": "2024-07-08",
  "returnDate": "2024-10-15",
  "book": {
    "id": 2,
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "publicationYear": "2018",
    "isbn": "9780134685991"
  },
  "patron": {
    "id": 2,
    "name": "Evan Drake",
    "contactInformation": "evan.drake@example.com"
  }

]
```

- Allow a patron to borrow a book.

```bash
POST /api/borrow/{bookId}/patron/{patronId}
```

Request Body: JSON representation contains the borrow date.

- Record the return of a borrowed book by a patron.

```bash
PUT /api/return/{bookId}/patron/{patronId}
```

Request Body: JSON representation contains the return date.

## 🔒 Security Configuration

The application utilizes Spring Security to ensure robust security measures. Here’s an overview of the security setup:

### 🛡️ Authentication

- Basic Authentication: HTTP Basic Authentication to secure the application. Users must provide a username and password to access protected endpoints.

### 🔐 Authorization

- Role-Based Access Control: Access to different API endpoints is restricted based on user roles. The roles and their permissions are as follows:
  - **USER**: Can access read-only endpoints for books.
  - **MANAGER**: Can access read-only endpoints for patrons and records.
  - **ADMIN**: Has full access, including create, update, and delete operations for books, patrons, and borrowing records.

### 🔑 User Details Management
- JDBC Authentication: User details and roles are managed using a JDBC-based authentication system. User credentials are stored securely with hashed passwords using Bcrypt.

### 🚫 Cross-Site Request Forgery (CSRF)
- CSRF Protection: CSRF protection is disabled for simplicity in local development. Ensure to enable CSRF protection in production environments for enhanced security.

## 🧪 Unit Testing

The Library Management System application includes a comprehensive suite of unit tests to ensure the functionality of the API endpoints. These tests are designed to validate key operations across the core entities of the system: **Books**, **Patrons**, and **Borrowing Records**.

### 🛠️ Testing Tools and Frameworks

- **JUnit 5**: Used for writing and executing test cases.
- **Spring Boot Test**: Provides testing support for Spring Boot applications.
- **MockMvc**: Facilitates testing of Spring MVC controllers by simulating HTTP requests and verifying responses.
- **Mockito**: Used to mock the service layer dependencies, enabling isolated testing of controllers.
- **Jackson ObjectMapper**: Helps in serializing and deserializing JSON objects within the tests.

### 🚀 How to Run the Tests

- **Ensure Your Environment is Set Up**: Make sure that your development environment is properly configured with the required dependencies. This includes having Maven and JDK installed, as well as setting up the necessary database configurations.
- **Run Tests via Maven**:
    - Execute the following command in the terminal to run all tests:
        ```bash
        mvn test
        ```
    - Alternatively, you can use your IDE's built-in testing features to run the test suite.

- **Verify Test Results**: After executing the tests, Maven will provide a summary of the results. Ensure that all tests pass successfully. If any tests fail, review the detailed logs to identify and resolve issues.



## ❗ Exception Handling

The application uses a global exception handler to manage errors gracefully. The following are the custom exceptions used:

- NotFoundException: Returns a 404 status when a resource is not found.
- BadRequestException: Returns a 400 status for bad requests.
- Generic Exception: Returns a 500 status for all other unhandled exceptions.

## 💻 Technology Stack

- Java 22
- Spring Boot 3.x
- Hibernate/JPA
- MySQL (or any other relational database)
- Maven for dependency management

## 📜 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
