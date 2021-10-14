FROM openjdk:11
RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

RUN mkdir customlibs
COPY lib/protocollo-ws.jar customlibs/protocollo-ws.jar

USER root
COPY anticorruzione.cer /usr/local/openjdk-11/lib/security
RUN \
    cd /usr/local/openjdk-11/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias anticorruzione -file anticorruzione.cer
    
    
    
ENTRYPOINT ["java","-Dspring.profiles.active=premaster","-cp","'app.jar:customlibs/*'","it.anac.segnalazioni.backend.SegnalazioniBackendApplication"]


#ENTRYPOINT ["java","-Dspring.profiles.active=premaster","-jar","/app.jar"]