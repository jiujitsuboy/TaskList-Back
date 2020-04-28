# TaskList-Back (Spring Boot 2 application for managing Users Task List)

This a API Rest Build with Spring Framework 5 which offer the following features

1. Two Rest Controllers (one for User validation other for Task Management)
2. Two Services containing the bussines logic for each controller.
3. Two models using JPA (User and Task entities)
4. Two repositories using Spring Data for operation between the H2 Database.
5. One HTTP Filter (OncePerRequestFilter) to obtain Authorization header and validate the JWT token (also renew the token if its valid).
6. One Aspect using Before Advice, to allow or avoid the request to reach the controller.

# Technologies Used

1. Spring Boot 2
2. Spring REST
3. Spring DATA
4. Spring JPA
5. Spring AOP 
6. Java Web Tokens
7. Maven 3

# Lanch Application

* Use any IDE (I used Eclipse) to load the Maven project.

*This app runs by default in http port 8080* 
*Use a embedded data base (h2)*
