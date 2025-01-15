
# README for NGR

## Overview

NGR (NextGenerationResearch)/Backend is a backend application developed using the Spring Boot framework. It provides various functionalities, such as web services, database operations, security, and email support. This project is structured to handle data persistence with relational (MySQL) and non-relational (MongoDB) databases, WebSocket communications, and JSON Web Tokens (JWT) for authentication.

## Features

- RESTful web services with `spring-boot-starter-web`
- Database integration with:
  - MySQL (Relational database)
  - MongoDB (NoSQL database)
- Security with Spring Security and JWT-based authentication
- Email service with `spring-boot-starter-mail`
- WebSocket support for real-time communication
- Actuator for monitoring and metrics
- Lombok for reducing boilerplate code
- Unit testing with JUnit 5

## Prerequisites

To build and run this project, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8 or higher
- MySQL Server
- MongoDB Server

## Installation

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd appBackSer
   ```

2. Configure the database:

   - For MySQL, update `application.properties` or `application.yml` with your database URL, username, and password.
   - Ensure MongoDB is running on the default port or update the configuration accordingly.

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

## Dependencies

The following are key dependencies used in the project:

### Spring Boot Starters:

- `spring-boot-starter-web`: For building REST APIs
- `spring-boot-starter-data-jpa`: For JPA-based persistence with MySQL
- `spring-boot-starter-data-mongodb`: For MongoDB operations
- `spring-boot-starter-security`: For security features
- `spring-boot-starter-actuator`: For monitoring
- `spring-boot-starter-websocket`: For WebSocket communication
- `spring-boot-starter-mail`: For email functionalities

### Additional Libraries:

- `Lombok`: Simplifies Java code by reducing boilerplate
- `Jackson`: For JSON processing
- `javax.servlet-api`: For servlet support
- `io.jsonwebtoken`: For JWT-based authentication

### Testing Dependencies:

- `spring-boot-starter-test`: For testing Spring Boot applications
- `JUnit 5`: For unit testing

## Configuration

### Database Configuration

Update the `application.properties` or `application.yml` file with your database credentials:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/your_database
```

### JWT Configuration

Set the secret key for JWT in the application properties:

```properties
jwt.secret=your_secret_key
```

### Email Configuration

Set your SMTP server details for sending emails:

```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
```

### Notify the developer

- You have the change the BasePath of the storage folder on 
```src\main\java\org\sid\appbackser\services\implementations\DepotServiceImp.java.
```

## Usage

- Access the RESTful APIs via tools like Postman or a frontend application.
- Use WebSocket endpoints for real-time communication.
- Monitor application health using Actuator endpoints (`/actuator/health`, `/actuator/info`).

## Testing

Run the test cases using Maven:

```bash
mvn test
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch: `git checkout -b feature-branch-name`
3. Commit your changes: `git commit -m 'Description of changes'`
4. Push to the branch: `git push origin feature-branch-name`
5. Open a pull request

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

## Contact

For any questions or issues, contact:
- [boutrasseytwassim@gmail.com](mailto:boutrasseytwassim@gmail.com)
- [amranihassan.am@gmail.com](mailto:amranihassan.am@gmail.com)
- [aymanaomaripro@gmail.com](mailto:aymanaomaripro@gmail.com)
```