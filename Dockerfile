FROM openjdk:11

COPY anticorruzione.cer /usr/local/openjdk-11/lib/security
RUN \
    cd /usr/local/openjdk-11/lib/security \
    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias anticorruzione -file anticorruzione.cer
    
ADD fonts /usr/share/fonts

RUN adduser --system --group spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY istat.csv istat.csv
COPY transcoding.csv transcoding.csv

COPY template_appalti.odt template_appalti.odt
COPY template_corruzione.odt template_corruzione.odt
COPY template_incarichi.odt template_incarichi.odt
COPY template_rpct.odt template_rpct.odt
COPY template_trasparenza.odt template_trasparenza.odt

ENTRYPOINT ["java","-Dspring.profiles.active=premaster","-jar","/app.jar"]