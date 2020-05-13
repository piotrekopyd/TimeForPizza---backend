FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE
ARG DATASOURCE_URL
ARG DATASOURCE_USERNAME
ARG DATASOURCE_PASSWORD
COPY ${JAR_FILE} app.jar
CMD java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -Dspring.datasource.url=${DATASOURCE_URL} -Dspring.datasource.username=${DATASOURCE_USERNAME} -Dspring.datasource.password=${DATASOURCE_PASSWORD} -jar /app.jar
