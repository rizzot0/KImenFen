FROM maven:3.8.5-openjdk-18 AS build
COPY pom.xml /KimenFen-1-0.0.1-SNAPSHOT/
COPY src /KimenFen-1-0.0.1-SNAPSHOT/src/

WORKDIR /KimenFen-1-0.0.1-SNAPSHOT

RUN mvn clean package -DskipTests

FROM openjdk:18.0.2.1-jdk-slim
WORKDIR /KimenFen-1-0.0.1-SNAPSHOT
COPY --from=build /KimenFen-1-0.0.1-SNAPSHOT/target/KimenFen-1-0.0.1-SNAPSHOT.jar /KimenFen-1-0.0.1-SNAPSHOT/KimenFen-1-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","jar","kimenfen.jar"]