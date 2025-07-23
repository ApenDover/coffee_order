FROM openjdk:17
WORKDIR /opt/coffee-client/
COPY build/libs/*1.jar coffee-client.jar
#COPY build/resources/main/KeyStore.jks KeyStore.jks
EXPOSE 8443
ENTRYPOINT ["java","-jar","coffee-client.jar"]
