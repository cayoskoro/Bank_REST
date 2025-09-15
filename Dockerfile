FROM openjdk:17
COPY target/*.jar bank.jar
ENTRYPOINT ["java", "-jar", "/bank.jar"]