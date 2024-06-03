FROM openjdk:21
EXPOSE 8080
ADD ./build/libs/EmotionsServer-1.0.jar ./emotions-server.jar
CMD ["java","-jar","emotions-server.jar"]