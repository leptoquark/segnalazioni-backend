# segnalazioni-backend
backend del form delle segnalazioni

L'esopsizione dei servizi è protetto da sicurezza con token JWT.

Sono previsti 2 profili:

1. dev
2. premaster

L'applicazione nel docker parte con il profilo premaster **-Dspring.profiles.active=premaster** che a differenza del profilo dev, usa i secret di openshift per le connessioni a mongo e credenziali di accesso al protocollo.

Sono attivi gli actuator per il monitoraggio con prometheus/graphana.

# Build
- prima di procedere alla build è necessario precaricare il file su repo m2

	mvn install:install-file -Dfile=lib\protocollo-ws.jar -DgroupId=it.anac.segnalazioni.client.protocollo -DartifactId=protocollo-ws -Dversion=1.0 -Dpackaging=jar

eseguire il comando **build.cmd** su windows oppure la sequenza dei comandi (evita i test per snellire la build) 

	mvnw clean install -DskipTests

# Push
- push su github

eseguire il comando **push.cmd** su windows oppure la sequenza dei comandi

	git add *
	git commit -m "aggiornamento"
	git push origin main

	
# Info generazione client protocollo
dentro la cartella tool ci sono i file per la generazione automatica del client e la produzione del pacchetto protocollo-ws.jar

	wsdl2java -clientjar protocollo-ws.jar protocolloWSSOAP_1.wsdl