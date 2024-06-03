FROM openjdk:21
EXPOSE 8080
COPY build/libs/EmotionsServer-1.0.jar /app/EmotionsServer.jar
CMD ["java","-jar","/app/EmotionsServer.jar"]
