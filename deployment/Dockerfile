FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY *.jar franquicias.jar
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
USER root
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar franquicias.jar" ]