# User Service

Spring Boot CRUD microservice for managing users.

## Stack

- Java 21
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA
- PostgreSQL
- Validation
- Lombok
- Actuator

## Database

Create the PostgreSQL database:

```sql
CREATE DATABASE userdb;
```

Update `src/main/resources/application.yml` if your PostgreSQL username/password differs.

## Run

```bash
mvn spring-boot:run
```

Service:

```text
http://localhost:8081
```

## APIs

### Create
POST `/api/users`

```json
{
  "name": "Arun Yadav",
  "email": "arun@example.com",
  "phone": "9876543210"
}
```

### Get all
GET `/api/users`

### Get by ID
GET `/api/users/{id}`

### Update
PUT `/api/users/{id}`

```json
{
  "name": "Arun Kumar",
  "email": "arun@example.com",
  "phone": "9876543210"
}
```

### Delete
DELETE `/api/users/{id}`

### Health
GET `/actuator/health`
