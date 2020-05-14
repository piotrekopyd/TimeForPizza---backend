FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE
ENV DATASOURCE_URL {$SPRING_DATASOURCE_URL}
ENV DATASOURCE_USERNAME {$SPRING_DATASOURCE_USERNAME}
ENV DATASOURCE_PASSWORD {$SPRING_DATASOURCE_PASSWORD}
COPY ${JAR_FILE} app.jar
CMD java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -Dspring.datasource.url=${DATASOURCE_URL} -Dspring.datasource.username=${DATASOURCE_USERNAME} -Dspring.datasource.password=${DATASOURCE_PASSWORD} -jar /app.jar
