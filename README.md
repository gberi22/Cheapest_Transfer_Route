# Cheapest Transfer Route Application

## Project Description

This Spring Boot application calculates the cheapest or most cost-effective transfer route based on weight and cost constraints. It uses a dynamic programming algorithm to maximize the total cost of transfers while adhering to a specified maximum weight.

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### 2. Build the Application

Use Maven to build the project:

```bash
mvn clean install
```

### 3. Run the Application

Start the application by running the following command:

```bash
mvn spring-boot:run
```

The application will start on port **8000**. If you need to change the port, update the `server.port` property in the `application.properties` file located in `src/main/resources/`.

---

## API Endpoints

### 1. **POST /api/transfers/inputRoutes**

- **Description:** Accepts a list of available transfers and the maximum weight allowed.
- **Request Body Example:**

```json
{
  "maxWeight": 15,
  "availableTransfers": [
    { "weight": 5, "cost": 10 },
    { "weight": 7, "cost": 14 },
    { "weight": 4, "cost": 7 }
  ]
}
```

- **Response:**

```http
HTTP/1.1 200 OK
```

- **Validation Errors:**
  - If the `maxWeight` is less than 1:
    ```http
    HTTP/1.1 400 BAD REQUEST
    {
      "maxWeight": "Maximum weight must be at least 1"
    }
    ```
  - If the list of transfers is empty:
    ```http
    HTTP/1.1 400 BAD REQUEST
    {
      "availableTransfers": "Available transfers list cannot be empty"
    }
    ```

### 2. **GET /api/transfers/getRoute**

- **Description:** Fetches the route with the highest cost while adhering to the weight constraint.
- **Response Example:**

```json
{
  "selectedTransfers": [
    { "weight": 7, "cost": 14 },
    { "weight": 5, "cost": 10 }
  ],
  "totalCost": 24,
  "totalWeight": 12
}
```

- **Error:**
  - If no transfers have been added:
    ```http
    HTTP/1.1 404 NOT FOUND
    ```
---

## CURL Examples

### 1. Adding Transfers (POST)

```bash
curl -X POST \
  http://localhost:8000/api/transfers/inputRoutes \
  -H "Content-Type: application/json" \
  -d '{
    "maxWeight": 15,
    "availableTransfers": [
      { "weight": 5, "cost": 10 },
      { "weight": 7, "cost": 14 },
      { "weight": 4, "cost": 7 }
    ]
  }'
```

### 2. Fetching the Route (GET)

```bash
curl -X GET http://localhost:8000/api/transfers/getRoute
```

---

## Tests

### Run Unit Tests

To execute the unit tests for this project, run:

```bash
mvn test
```

Test cases are located in the `src/test/java` folder. They cover validation, service logic, and controller responses.

---

