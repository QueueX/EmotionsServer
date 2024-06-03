FROM openjdk:21.0.2
EXPOSE 8080
ADD ./build/libs/EmotionsServer-1.0-plain.jar emotions-server.jar
CMD ["java","-jar","emotions-server.jar"]