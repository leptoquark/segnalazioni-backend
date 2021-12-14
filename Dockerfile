FROM openjdk:11

COPY anticorruzione.cer /usr/local/openjdk-11/lib/security
RUN \
    cd /usr/local/openjdk-11/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias anticorruzione -file anticorruzione.cer

RUN adduser --system --group spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY istat.csv istat.csv
COPY template_appalti.odt template_appalti.odt
COPY amp.properties amp.properties

ENTRYPOINT ["java","-Dspring.profiles.active=premaster","-jar","/app.jar"]