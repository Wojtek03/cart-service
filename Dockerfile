FROM openjdk:21-jdk
MAINTAINER wojciechbiernacki
COPY target/cart-service-0.0.1-SNAPSHOT.jar cart-service.jar
ENTRYPOINT ["java", "-jar", "/cart-service.jar"]
