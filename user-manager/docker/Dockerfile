FROM openjdk:11-jre-slim

VOLUME /tmp

COPY build/libs/user-manager-0.0.1-SNAPSHOT.jar '/app.jar'

EXPOSE 8080 5005

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
