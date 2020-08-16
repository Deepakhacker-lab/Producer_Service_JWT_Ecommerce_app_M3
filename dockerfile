FROM openjdk:8-jdk-alpine
COPY target/37-thymeleafdemo-employees-delete-0.0.1-SNAPSHOT.jar 37-thymeleafdemo-employees-delete-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java -jar /37-thymeleafdemo-employees-delete-0.0.1-SNAPSHOT.jar"]