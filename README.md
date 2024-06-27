# Bitespeed Challenge

## Hosting details

- Hosted on Render - bitespeed-ckg4.onrender.com
- For best testing experience use Swagger - https://bitespeed-ckg4.onrender.com/swagger-ui/index.html
- DB Webview - https://bitespeed-ckg4.onrender.com/h2-console/login.jsp
    - Driver Class: org.h2.Driver
    - JDBC URL: jdbc:h2:file:/db
    - User: sa
    - Password is empty

## Local Setup

- Compile the project (JDK 21 is required)
```bash
mvn clean install
```

- Run the project
```bash
mvn spring-boot:run
```
Or
```
java -jar target/bitespeed-0.0.1-SNAPSHOT.jar
```
