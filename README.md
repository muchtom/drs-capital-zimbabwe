# Loan Eligibility API

A Spring Boot REST API for checking loan eligibility based on salary verification, credit history, and other eligibility criteria.

## Features

-  Check loan eligibility based on multiple criteria
-  Mock salary verification service
-  Mock credit bureau service
-  Automatic eligibility calculation
-  Detailed decline reasons when application is rejected
-  Swagger/OpenAPI documentation
-  Database persistence of all applications

## Technology Choices

### Backend Framework
- **Spring Boot 4.0.0**
- Chosen for its robust ecosystem, dependency injection, and rapid development capabilities. Spring Boot provides excellent integration with databases, REST APIs, and third-party services.

### Database
- **MySQL**
- Relational database for storing loan applications. MySQL was chosen for its reliability, wide adoption, and good performance for transactional data.

### ORM
- **Hibernate/JPA**
- Simplifies database operations and provides object-relational mapping. JPA allows for clean entity management and automatic schema generation.

### API Documentation
- **SpringDoc OpenAPI 2.8.9**
- Automatically generates interactive API documentation from code annotations. Makes it easy for developers to understand and test the API without additional documentation effort.

### Build Tool
- **Maven**
- Dependency management and build automation. Provides consistent builds across different environments.

### Validation
- **Jakarta Validation**
- Built-in validation annotations for request validation, reducing boilerplate code and ensuring data integrity at the API layer.

### Why These Choices?
- **Simplicity**
- The stack is straightforward and well documented, making it easy for developers to understand and maintain
- **Industry Standard**
- All technologies are widely used in enterprise applications
- **Scalability**
- The architecture can easily be extended to integrate with real external services

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 5.7+ (or MySQL 8.0+)
- IDE (IntelliJ IDEA, Eclipse, etc.)

## Running Locally

### Prerequisites

Before running the application, ensure you have

1. **Java 17** installed and configured
   ```bash
   java -version  # Should show version 17 or higher
   ```

2. **Maven** installed (or use the Maven wrapper)
   ```bash
   mvn -version
   ```

3. **MySQL** server running locally
   - Default port: 3306
   - Make sure MySQL service is started

### Step-by-Step Setup

#### 1. Clone/Download the Project
```bash
cd loan
```

#### 2. Configure Database Connection

Edit `src/main/resources/application.properties` and update if your MySQL credentials differ:

```properties
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/grsLoanApplication?...
```

**Note** - The application will automatically create the database `grsLoanApplication` on first run if it doesn't exist.

#### 3. Build the Project

Using Maven:
```bash
mvn clean install
```

Or using Maven wrapper (if available):
```bash
./mvnw clean install
```

#### 4. Run the Application

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using IDE**
- Open the project in IntelliJ IDEA or Eclipse
- Navigate to `src/main/java/com/buffaloVentures/loan/LoanApplication.java`
- Right-click and select "Run LoanApplication"

**Option 3: Using JAR**
```bash
mvn clean package
java -jar target/loan-1.0.0.jar
```

#### 5. Verify Application is Running

- Check console logs for: `Started LoanApplication in X.XXX seconds`
- Application should be available at: `http://localhost:8052`
- Swagger UI: `http://localhost:8052/swagger-ui.html`
- API Docs: `http://localhost:8052/api-docs`

#### 6. Test the API

You can test using:
- **Swagger UI**: Navigate to `http://localhost:8052/swagger-ui.html` and use the interactive interface
- **cURL**: See example commands below
- **Postman**: Import the OpenAPI spec from `/api-docs`

### Troubleshooting

**Port Already in Use:**
- Change the port in `application.properties`: `server.port=8053`

**Database Connection Failed:**
- Verify MySQL is running: `mysql -u root -p`
- Check credentials in `application.properties`
- Ensure MySQL allows connections from localhost

**Build Errors:**
- Clean Maven cache: `mvn clean`
- Update dependencies: `mvn dependency:resolve`
- Check Java version: Must be Java 17 or higher

## API Endpoints

### Main Endpoint

#### Check Loan Eligibility
```
POST /api/loans/eligibility
Content-Type: application/json
```

**Request Body:**
```json
{
  "nationalId": "1234567890",
  "loanAmount": 50000.00,
  "termInMonths": 12
}
```

**Response (Eligible):**
```json
{
  "eligible": true,
  "declineReasons": null,
  "monthlyRepayment": 4166.67,
  "monthlySalary": 15000.00,
  "creditScore": 650,
  "activeLoansCount": 1,
  "hasActiveDefaults": false
}
```

**Response (Not Eligible):**
```json
{
  "eligible": false,
  "declineReasons": [
    "Salary too low. Need: 12500.00, Have: 8000.00",
    "Credit score too low. Need: 600, Have: 550"
  ],
  "monthlyRepayment": 4166.67,
  "monthlySalary": 8000.00,
  "creditScore": 550,
  "activeLoansCount": 2,
  "hasActiveDefaults": true
}
```

### Mock Service Endpoints

#### Salary Verification
```
GET /api/salary/verify/{nationalId}
```

**Example:**
```
GET /api/salary/verify/1234567890
```

**Response:**
```json
{
  "success": true,
  "monthlySalary": 15000.00,
  "errorMessage": null
}
```

#### Credit Bureau Check
```
GET /api/credit/check/{nationalId}
```

**Example:**
```
GET /api/credit/check/1234567890
```

**Response:**
```json
{
  "success": true,
  "creditScore": 650,
  "activeLoansCount": 1,
  "hasActiveDefaults": false,
  "errorMessage": null
}
```

## Eligibility Rules

The API checks the following criteria:

1. **Salary Requirement**: Monthly salary must be at least 3x the monthly repayment amount
2. **Credit Score**: Must be 600 or above
3. **Active Defaults**: Applicant must not have any active defaults
4. **Active Loans**: Maximum of 3 active loans allowed

If any of these rules are violated, the application is declined with specific reasons provided.

## Swagger UI

Once the application is running, access Swagger UI at:
```
http://localhost:8052/swagger-ui.html
```

You can test all endpoints directly from the Swagger interface.

## Mock Data

The application includes pre-populated mock data for testing:

| National ID | Salary | Credit Score | Active Loans | Has Defaults |
|------------|--------|--------------|-------------|--------------|
| 1234567890 | 15,000 | 650 | 1 | No |
| 0987654321 | 25,000 | 750 | 0 | No |
| 1122334455 | 8,000 | 550 | 2 | Yes |
| 5566778899 | 30,000 | 800 | 0 | No |

For new national IDs not in the mock database, random values will be generated.

## Technology Stack

- **Framework**: Spring Boot 4.0.0
- **Java Version**: 17
- **Database**: MySQL
- **ORM**: Hibernate/JPA
- **API Documentation**: SpringDoc OpenAPI 2.8.9
- **Build Tool**: Maven
- **Validation**: Jakarta Validation

## Project Structure

```
loan/
├── docs/
│   └── images/              # Images for README documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/buffaloVentures/loan/
│   │   │       ├── api/              # REST Controllers
│   │   │       ├── config/           # Configuration classes
│   │   │       ├── DTO/              # Data Transfer Objects
│   │   │       ├── entity/           # JPA Entities
│   │   │       ├── exception/        # Exception handlers
│   │   │       ├── repository/       # JPA Repositories
│   │   │       └── service/           # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```


## Example Usage

### Using cURL

```bash
# Check loan eligibility
curl -X POST http://localhost:8052/api/loans/eligibility \
  -H "Content-Type: application/json" \
  -d '{
    "nationalId": "1234567890",
    "loanAmount": 50000.00,
    "termInMonths": 12
  }'

# Verify salary
curl http://localhost:8052/api/salary/verify/1234567890

# Check credit history
curl http://localhost:8052/api/credit/check/1234567890
```

### Using Postman

1. Import the API endpoints from Swagger UI
2. Set the base URL to `http://localhost:8052`
3. Test the endpoints with the provided examples

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK`: Successful request
- `400 Bad Request`: Invalid input parameters
- `503 Service Unavailable`: External service (salary/credit) unavailable

## Notes

- The salary verification and credit bureau services are mock implementations
- In a production environment, these would be external API calls
- The services simulate network delays and occasional failures (5% chance)
- All loan applications are persisted to the database

## Future Improvements

Given more time, here are the improvements I would prioritize:

### 1. **External Service Integration**
   - Replace mock services with actual HTTP clients for salary verification and credit bureau APIs
   - Add proper error handling for external service failures
   - Implement caching for frequently accessed credit/salary data

### 2. **Security Enhancements**
   - Add Spring Security for API authentication and authorization
   - Implement JWT token-based authentication
   - Add rate limiting to prevent abuse
   - Encrypt sensitive data (national IDs, salary information)
   - Add input sanitization to prevent injection attacks

### 3. **Testing**
   - Add comprehensive unit tests for services and controllers
   - Integration tests for API endpoints
   - Mock external services in tests
   - Performance/load testing

### 4. **Code Quality**
   - Add more detailed logging with structured logging (Logback/Log4j2)
   - Implement proper exception hierarchy
   - Add request/response logging middleware
   - Code quality tools (SonarQube, Checkstyle)
   - API versioning strategy

### 5. **Database Improvements**
   - Implement soft deletes for loan applications
   - Add indexes for frequently queried fields
   - Database connection pooling optimization
   - Add audit fields (createdBy, updatedBy, timestamps)

### 6. **API Enhancements**
   - Add pagination for listing loan applications
   - Implement filtering and sorting
   - Add endpoints to retrieve application history
   - Add webhook support for async processing
   - Implement idempotency keys for duplicate request prevention

### 7. **Monitoring & Observability**
   - Add health check endpoints
   - Integrate with monitoring tools (Prometheus, Grafana)
   - Add distributed tracing (Zipkin, Jaeger)
   - Implement metrics collection
   - Add alerting for critical errors

### 8. **Documentation**
   - Add more detailed API documentation with examples
   - Create Postman collection
   - Add architecture diagrams
   - Document deployment procedures
   - Add developer onboarding guide



## Author

Bliss Muchemwa  
Email: muchemwatatendab@gmail.com

## License

TATENDA

