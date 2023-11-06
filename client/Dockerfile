FROM eclipse-temurin:17.0.8.1_1-jre-jammy
WORKDIR /opt/coffee-client/
COPY build/libs/*1.jar coffee-client.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","coffee-client.jar"]