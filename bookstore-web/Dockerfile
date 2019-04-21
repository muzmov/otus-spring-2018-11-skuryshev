FROM maven:alpine as builder

WORKDIR /usr/app
ADD pom.xml .
RUN mvn dependency:go-offline

ADD . .
RUN mvn clean install

FROM java:8-alpine
WORKDIR /usr/app
COPY --from=builder /usr/app/target/bookstore.jar .
EXPOSE 8080
CMD ["java", "-jar", "bookstore.jar"]


