# Bank Application

This is a simple bank application built with Java, Spring Boot, and Maven.

## Project Structure

The project is organized into several packages:

- `com.bank.models`: Contains the entity classes for `Account`, `Customer`, and `Transaction`.
- `com.bank.repositories`: Contains the Spring Data JPA repositories for `Account`, `Customer`, and `Transaction`.
- `com.bank.services`: Contains the service classes for `Account`, `Customer`, and `Transaction`.

## Entities

- `Account`: Represents a bank account. Attributes include `id`, `account_number`, `account_type`, `balance`, and `customer_id` (foreign key to `Customer`).
- `Customer`: Represents a bank customer. Attributes include `id`, `first_name`, `last_name`, `email`, `phone`, and `address`.
- `Transaction`: Represents a bank transaction. Attributes include `id`, `amount`, `type`, `date`, and `account_id` (foreign key to `Account`).

## Database Configuration

The application uses a MySQL database. The database configuration is specified in the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/master_data
spring.datasource.username=root
spring.datasource.password=${db_password}
spring.jpa.hibernate.ddl-auto=update
```
Running the Application
To run the application, use the following command in the project root directory:
```
mvn spring-boot:run
```


