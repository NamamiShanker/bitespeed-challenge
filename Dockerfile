FROM openjdk:21-jdk
EXPOSE 8080
ARG JAR_FILE=target/*jar
COPY ${JAR_FILE} app.jar
RUN mkdir /db
RUN mkdir /logs
ENTRYPOINT ["java","-jar","/app.jar"]