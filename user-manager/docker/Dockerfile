FROM openjdk:11-jre-slim

VOLUME /tmp

COPY build/libs/user-manager-0.0.1-SNAPSHOT.jar '/app.jar'

EXPOSE 8080 5005

ENTRYPOINT ["java","-jar","/app.jar"]
