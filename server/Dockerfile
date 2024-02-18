FROM openjdk:17
WORKDIR /opt/coffee-back/
COPY build/libs/*1.jar coffee-back.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://psql:5432/coffee_db
EXPOSE 8080
ENTRYPOINT ["java","-jar","coffee-back.jar"]