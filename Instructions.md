# Popcorn Palace

## Table of Contents
* [General Info](#general-info)
* [Setup & Run Instructions](#setup--run-instructions)


---

## General Info
This project is part of the AT&T Tech Development Program Assignment.
It is a backend microservice for managing cinema-related operations, including movies, showtimes, and bookings.

Technical Specifications:

* Programming Language: Java 21
* Framework: Spring Boot
* Database: PostgreSQL
* Containerization: Docker and Docker Compose
* Build Tool: Maven


Prerequisites:

* Git
* Docker and Docker Compose
* Maven

---

## Setup & Run Instructions

### Build the Application

1. Clone the repo:
```bash
git clone https://github.com/mayabenzeev/popcorn-palace.git
cd popcorn-palace
```
2. Initialize Database Containers:
```bash
docker compose up --build
```
3. Build and Run the Application:
 ```bash
mvn clean package
mvn spring-boot:run
```

4. Execute Unit Tests:
 ```bash
mvn test
```

5. API Interaction:
    * API requests can be executed using the provided Postman collection file, `popcorn.postman_collection.json`. Import this file into Postman to interact with the microservice's endpoints.


