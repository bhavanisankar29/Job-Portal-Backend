# JobPortalBackend

JobPortalBackend is a Spring Boot REST API for a job portal application. It supports authentication with JWT, recruiter and job seeker flows, job posting and applications, and admin management of user profiles.

## Features

- JWT-based sign-in and stateless security
- Recruiter registration and job management
- Job seeker registration and job application flow
- Admin endpoints for listing and removing recruiters and job seekers
- Swagger/OpenAPI documentation
- Spring Data JPA persistence with MySQL

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- MySQL
- Lombok
- JWT

## Project Structure

- src/main/java/com/application/jobportalbackend
  - config: security and OpenAPI configuration
  - controller: REST endpoints
  - dto: request and response models
  - entity: JPA entities
  - repository: Spring Data repositories
  - security: JWT filter, token service, and user details service
  - service: application business logic
- src/main/resources/application.properties: application configuration
- src/test/java: tests

## Prerequisites

- Java 17
- Maven
- MySQL 8 or compatible database

## Configuration

The application reads its settings from src/main/resources/application.properties.

Update these values for your local environment:

- spring.datasource.url
- spring.datasource.username
- spring.datasource.password

The default configuration expects a MySQL database named **jobportal** on localhost.

## Run the Application

From the project root:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```bash
mvnw.cmd spring-boot:run
```

## Build and Test

```bash
./mvnw clean test
```

or on Windows:

```bash
mvnw.cmd clean test
```

To build the jar:

```bash
./mvnw clean package
```

## API Overview

Authentication:

- POST /auth/recruiter/signup
- POST /auth/jobSeeker/signup
- POST /auth/signin

Admin:

- GET /admin/getAllJobSeekers
- GET /admin/getAllRecruiters
- DELETE /admin/removeProfile/jobSeeker/{jobSeekerId}
- DELETE /admin/removeProfile/recruiter/{recruiterId}

Recruiter:

- GET /recruiters/allJobsPosted/{recruiterId}
- DELETE /recruiters/{recruiterId}/deleteJob/{jobId}
- POST /recruiters/postJob/{recruiterId}
- PUT /recruiters/updateJob/{recruiterId}/{jobId}
- PUT /recruiters/updateStatus/{recruiterId}/{jobId}/{jobSeekerId}
- GET /recruiters/{recruiterId}/{jobId}/allApplications

Job Seeker:

- POST /jobSeekers/applyJob/{jobId}/{jobSeekerId}
- DELETE /jobSeekers/withdrawApplication/{jobId}/{jobSeekerId}
- GET /jobSeekers/allJobs
- GET /jobSeekers/allAppliedJobs/{jobSeekerId}
- GET /jobSeekers/{jobSeekerId}/jobStatus/{status}
- GET /jobSeekers/job/{jobId}

> **Note:** Detailed information about the endpoints are in **apiDetails.txt** file.

## Swagger UI

After starting the application, open:

- /swagger-ui/index.html

## Author

Bhavani Sankar Katta - https://github.com/bhavanisankar29
