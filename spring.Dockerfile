FROM openjdk:17-jdk-slim AS builder

WORKDIR /webapp

COPY mvnw .
COPY pom.xml .
COPY .mvn/ .mvn/

RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline -B

COPY . .

RUN ./mvnw package -DskipTests


FROM openjdk:17-jdk-slim

COPY --from=builder /webapp/target/AnimalChipization-0.0.1.jar ./AnimalChipization-0.0.1.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "./AnimalChipization-0.0.1.jar"]
CMD ["--spring.profiles.active=prod", "-Xms256m", "-Xmx1024m"]