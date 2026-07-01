# patient-web-app
# Patient Web Application with Search and CRUD Capability

This repository contains a patient management web application developed for the technical assessment.

The project includes:

- Spring Boot backend REST API
- Angular frontend
- PostgreSQL database
- Patient CRUD functionality
- Search by PID or patient name
- Server-side pagination
- Task 1 MPI design and matching algorithm (Included in the ZIP file)

## Database Setup
SQL: CREATE SCHEMA IF NOT EXISTS patient_app;

* The application uses Spring Data JPA with Hibernate ddl-auto=update for local development.
* The PostgreSQL database must be created manually before running the backend.
* The application uses "patient_app" as the PostgreSQL schema. 
* Before running the backend, make sure the "patient_app" schema exists in the configured database.

# Run Backend
``` bash
cd backend
mvn spring-boot:run
```
* Backend runs on: http://localhost:8080

# Run Frontend
``` bash
cd frontend
npm install
ng serve
```
* Frontend runs on: http://localhost:4200

# Run Backend Tests
``` bash
cd backend
mvn test
```

## API Test Collection
A curl/Postman test file is included inside the `Backend` folder.

* Git Repository: https://github.com/ArtOfStevin/patient-web-app.git
