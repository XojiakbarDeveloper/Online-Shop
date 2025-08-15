# 1. Java image tanlaymiz
FROM openjdk:17-jdk-slim

# 2. Ilovani container ichiga nusxalash
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# 3. Spring Boot ilovani ishga tushirish
ENTRYPOINT ["java", "-jar", "/app.jar"]
