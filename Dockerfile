FROM openjdk:11
RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

USER root
COPY anticorruzione.cer $JAVA_HOME/jre/lib/security
RUN \
    cd $JAVA_HOME/jre/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias anticorruzione -file anticorruzione.cer

ENTRYPOINT ["java","-jar","/app.jar"]