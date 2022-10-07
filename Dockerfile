FROM openjdk:17-alpine
COPY target/chat-0.0.1-SNAPSHOT-jar-with-dependencies.jar /chat.jar
CMD ["java", "-jar","/chat.jar"]
EXPOSE 5432
