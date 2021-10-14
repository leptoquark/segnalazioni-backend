# segnalazioni-backend
backend del form delle segnalazioni

L'esopsizione dei servizi è protetto da sicurezza con token JWT.

# Build
- prima di procedere alla build è necessario precaricare il file su repo m2

	mvn install:install-file -Dfile=lib\protocollo-ws.jar -DgroupId=it.anac.segnalazioni.client.protocollo -DartifactId=protocollo-ws -Dversion=1.0 -Dpackaging=jar

eseguire il comando **build.cmd** su windows oppure la sequenza dei comandi

	mvnw clean install -DskipTests

# Push
- push su github

eseguire il comando **push.cmd** su windows oppure la sequenza dei comandi

	git add *
	git commit -m "aggiornamento"
	git push origin main
