package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import it.anac.segnalazioni.backend.report.model.Carenza;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneTrasparenza;

public class ReportHelperTrasparenza extends ReportHelperJson {
	
	private Logger logger = LoggerFactory.getLogger(ReportHelperTrasparenza.class);
	
	public ReportHelperTrasparenza(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneTrasparenza createTrasparenzaFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 
		segnalante.setEnte(org);
		
		String sito_web = getValueFromJson(nameNode, "sito_web");
		String contenuto_segnalazione = getValueFromJson(nameNode, "contenutosegnalazione");
		
		String contenuto_segnalazione_val = "Carenze sulla sezione Amministrazione/Società Trasparente";
		if (contenuto_segnalazione.equals("assenza"))
			contenuto_segnalazione_val = "Assenza sezione Amministrazione/Società Trasparente";

				
		SegnalazioneTrasparenza segnalazione = new SegnalazioneTrasparenza(segnalante, new Date(), org,
				sito_web, contenuto_segnalazione_val);
		
		// Amministrazione trasparente
		JsonNode arrNode_sezione = nameNode.get("sezionesegnalazione");
		if (arrNode_sezione!=null)
		{
			logger.info("Amministrazione Trasparente:\n"+arrNode_sezione.toPrettyString());
			if (arrNode_sezione.isArray()) {
			    for (JsonNode objNode : arrNode_sezione) {
			    	String sottosezione_aux = "sottosezione_"+objNode.asText();
			    	Carenza carenza = new Carenza(sottosezione_aux);
			    	JsonNode arrNode_sottosezione = nameNode.get(sottosezione_aux);
			    	if (arrNode_sottosezione!=null)
				    	if (arrNode_sottosezione.isArray())
				    	{
				    		 for (JsonNode objNodeSub : arrNode_sottosezione) {
				    			 carenza.addSezione(objNodeSub.asText());
				    		 }
				    		 segnalazione.addCarenza(carenza);
				    	}    		
			    }
			}
		}
		
		// Società trasparente		
		arrNode_sezione = nameNode.get("sezionesegnalazione2");
		if (arrNode_sezione!=null)
		{
			logger.info("Societa Trasparente: \n"+arrNode_sezione.toPrettyString());
			if (arrNode_sezione.isArray()) {
			    for (JsonNode objNode : arrNode_sezione) {
			    	String sottosezione_aux = "sottosezione2_"+objNode.asText();
			    	Carenza carenza = new Carenza(sottosezione_aux);
			    	JsonNode arrNode_sottosezione = nameNode.get(sottosezione_aux);
			    	if (arrNode_sottosezione!=null)
			    	if (arrNode_sottosezione.isArray())
			    	{
			    		 for (JsonNode objNodeSub : arrNode_sottosezione) {
			    			 carenza.addSezione(objNodeSub.asText());
			    		 }
			    		 segnalazione.addCarenza(carenza);
			    	}    		
			    }
			}
		}
		
		chiusura(segnalazione);
		
		return segnalazione;
	}

}
