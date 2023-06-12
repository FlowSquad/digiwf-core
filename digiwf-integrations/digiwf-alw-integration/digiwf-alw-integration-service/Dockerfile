FROM amazoncorretto:11

EXPOSE 8080

COPY target/*.jar /deployments/spring-boot-application.jar

CMD "java" ${JAVA_OPTIONS} "-jar" "/deployments/spring-boot-application.jar"
