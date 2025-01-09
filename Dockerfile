FROM openjdk:17-jdk-alpine
COPY target/heart_rate.war heart_rate/heart_rate.war

WORKDIR /heart_rate
EXPOSE 8181

ENTRYPOINT ["java", "-jar","heart_rate.war"]