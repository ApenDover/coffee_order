FROM openjdk:17
WORKDIR /opt/coffee-client/
COPY build/libs/*1.jar coffee-client.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","coffee-client.jar"]