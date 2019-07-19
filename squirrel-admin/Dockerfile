FROM openjdk:7-jre-slim
MAINTAINER xijn

ENV PARAMS=""

ADD target/squirrel-admin-*.jar /app.jar

#ENTRYPOINT ["java", "-jar", "$PARAMS /app.jar"]
ENTRYPOINT ["sh","-c","java -jar /app.jar $PARAMS"]
