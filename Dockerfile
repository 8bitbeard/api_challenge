FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} itauchallenge.jar
ENTRYPOINT ["java", "-jar", "itauchallenge.jar"]