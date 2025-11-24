# Seller App (Spring MVC + Embedded Tomcat + JPA + H2)

A non-Spring-Boot web application.  
Manually bootstrapped Spring MVC inside embedded Tomcat.  
Uses JPA/Hibernate, H2 in-memory DB, Redis, and a custom data loader.

## Project Structure

src/main/java/com/dachi/sellerapp
config/         → Spring configs (Tomcat, WebConfig, Security, JPA, Redis)
controller/     → REST controllers
service/        → Services
repository/     → Spring Data repositories
model/          → JPA entities
dto/            → DTOs
MainApplication → entry point

src/main/resources
application.properties
schema.sql      → DB schema
data.sql        → Initial test data (loaded by DataLoader)

## How the app starts

`MainApplication` creates and runs an embedded Tomcat.  
`WebAppInitializer` builds the Spring context manually and registers the DispatcherServlet.  
`AppConfig` + `PersistenceConfig` configure JPA/H2.


### Install dependencies (download all libs)
```bash
mvn dependency:resolve
```
### Build the JAR
```bash
mvn clean package
```

### Run the application
```bash
java -jar target/sellerapp-1.0-SNAPSHOT.jar
```
