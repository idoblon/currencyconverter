# Currency Converter

A RESTful API application built with Spring Boot that provides real-time currency conversion functionality with user management and conversion history tracking.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)

## ✨ Features

- **Real-time Currency Conversion**: Fetch live exchange rates from ExchangeRate-API
- **User Management**: Complete CRUD operations for user accounts
- **Conversion History**: Track and manage all currency conversion transactions
- **Pagination Support**: Efficient data retrieval with pagination
- **RESTful API**: Well-structured REST endpoints
- **Database Persistence**: MySQL database integration with JPA/Hibernate
- **Logging**: Comprehensive logging with SLF4J

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 4.0.1**
  - Spring Data JPA
  - Spring Web MVC
  - Spring REST Client
- **MySQL** - Database
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management
- **ExchangeRate-API** - Currency exchange rates provider

## 📦 Prerequisites

Before running this application, ensure you have:

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- An API key from [ExchangeRate-API](https://www.exchangerate-api.com/)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CurrencyConverter
   ```

2. **Create MySQL database**
   ```sql
   CREATE DATABASE currencyconverter;
   ```

3. **Configure application properties**
   
   Update `src/main/resources/application.properties` with your database credentials and API key:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/currencyconverter
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   exchange.api.key=your_api_key
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## ⚙️ Configuration

Key configuration properties in `application.properties`:

| Property | Description | Default Value |
|----------|-------------|---------------|
| `server.port` | Application server port | 8080 |
| `spring.datasource.url` | MySQL database URL | jdbc:mysql://localhost:3306/currencyconverter |
| `spring.jpa.hibernate.ddl-auto` | Hibernate DDL mode | update |
| `exchange.api.key` | ExchangeRate-API key | - |
| `exchange.api.url` | Base URL for exchange API | https://v6.exchangerate-api.com/v6/ |
| `exchange.cache.duration` | Cache duration in minutes | 60 |

## 📡 API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/users?page={page}&size={size}` | Get all users (paginated) |
| GET | `/api/v1/{id}` | Get user by ID |
| POST | `/api/v1/add` | Create new user |
| PUT | `/api/v1/update/{id}` | Update user by ID |
| DELETE | `/api/v1/delete/{id}` | Delete user by ID |

### Conversion Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/conversionhistory?page={page}&size={size}` | Get all conversion history (paginated) |
| GET | `/api/v1/conversion/{id}` | Get conversion by ID |
| POST | `/api/v1/conversion/add` | Create new conversion record |
| PUT | `/api/v1/conversion/update/{id}` | Update conversion by ID |
| DELETE | `/api/v1/conversion/delete/{id}` | Delete conversion by ID |
| GET | `/api/v1/currencyRates` | Get current currency exchange rates |

## 🗄️ Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(255)
);
```

### Conversion History Table

```sql
CREATE TABLE conversionhistory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    from_currency VARCHAR(10),
    to_currency VARCHAR(10),
    amount FLOAT,
    converted_amount FLOAT,
    exchange_rate FLOAT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 💡 Usage Examples

### Create a User

```bash
curl -X POST http://localhost:8080/api/v1/add \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "address": "123 Main St"
  }'
```

### Create a Conversion Record

```bash
curl -X POST http://localhost:8080/api/v1/conversion/add \
  -H "Content-Type: application/json" \
  -d '{
    "user": {"id": 1},
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "amount": 100,
    "convertedAmount": 85.50,
    "exchangeRate": 0.855
  }'
```

### Get Currency Rates

```bash
curl http://localhost:8080/api/v1/currencyRates
```

### Get All Users (Paginated)

```bash
curl "http://localhost:8080/api/v1/users?page=0&size=10"
```

### Get Conversion History (Paginated)

```bash
curl "http://localhost:8080/api/v1/conversionhistory?page=0&size=10"
```

## 📁 Project Structure

```
CurrencyConverter/
├── src/
│   ├── main/
│   │   ├── java/com/javaspring/currencyconverter/
│   │   │   ├── controller/
│   │   │   │   ├── ConversionController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entities/
│   │   │   │   ├── Conversion.java
│   │   │   │   └── User.java
│   │   │   ├── pojo/
│   │   │   │   ├── request/
│   │   │   │   │   └── CurrencyRateRequestPojo.java
│   │   │   │   └── response/
│   │   │   │       └── CurrencyRateResponsePojo.java
│   │   │   ├── repository/
│   │   │   │   ├── ConversionRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── services/
│   │   │   │   ├── ConversionService.java
│   │   │   │   ├── CurrencyConversionService.java
│   │   │   │   ├── CurrencyConversionServiceImpl.java
│   │   │   │   └── UserService.java
│   │   │   ├── util/
│   │   │   │   ├── CurrencyConversionApiCaller.java
│   │   │   │   └── config/
│   │   │   │       └── RestClientConfig.java
│   │   │   └── CurrencyConverterApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Your Name**

## 🙏 Acknowledgments

- [ExchangeRate-API](https://www.exchangerate-api.com/) for providing currency exchange rates
- Spring Boot team for the excellent framework
- All contributors who help improve this project
