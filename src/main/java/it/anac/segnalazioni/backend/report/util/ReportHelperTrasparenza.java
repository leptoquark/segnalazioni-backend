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
	
	private String getSezione(String val)
	{
		String ret = ND;
		
		if (val.equals("disposizioniGenerali"))
			ret = "Disposizioni Generali";
		else if (val.equals("organizzazione"))
			ret = "Organizzazione";
		else if (val.equals("consulentiECollaboratori"))
			ret = "Consulenti e Collaboratori";
		else if (val.equals("personale"))
			ret = "Personale";
		else if (val.equals("bandiDiConcorso"))
			ret = "Bandi di Concorso";
		else if (val.equals("performance"))
			ret = "Performance";
		else if (val.equals("entiControllati"))
			ret = "Enti Controllati";
		else if (val.equals("attivitaEProcedimenti"))
			ret = "Attivita e procedimenti";
		else if (val.equals("provvedimenti"))
			ret = "Provvedimenti";
		else if (val.equals("bandiDiGaraEContratti"))
			ret = "Bandi di Gara e Contratti";
		else if (val.equals("sovvenzioniContributiSussidiVantaggiEconomici"))
			ret = "Sovvenzioni Contributi Sussidi Vantaggi Economici";
		else if (val.equals("bilanci"))
			ret = "Bilanci";
		else if (val.equals("beniImmobiliEGestionePatrimonio"))
			ret = "Beni Immobili e Gestione Patrimonio";
		else if (val.equals("controlliERilieviSullamministrazione"))
			ret = "Cotrolli e Rilievi sull'Amministrazione";
		else if (val.equals("serviziErogati"))
			ret = "Servizi Erogati";
		else if (val.equals("pagamentiAllamministrazione"))
			ret = "Pagamenti All'Amministrazione";
		else if (val.equals("operePubbliche"))
			ret = "Opere Pubbliche";
		else if (val.equals("pianificazioneEGovernoDelTerritorio"))
			ret = "Pianificazione e Governo del Territorio";
		else if (val.equals("informazioniAmbientali"))
			ret = "Informazioni Ambientali";
		else if (val.equals("struttureSanitariePrivateAccreditate"))
			ret = "Strutture Sanitarie Private Accreditate";
		else if (val.equals("interventiStraordinariEDiEmergenza"))
			ret = "Interventi Straordinari e di Emergenza";
		else if (val.equals("altriContenuti"))
			ret = "Altri Contenuti";
		
		else if (val.equals("selezioneDelPersonale"))
			ret = "Selezione del Personale";
		else if (val.equals("pagamenti"))
			ret = "pagamenti"; 

		return ret;
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
			    	String sottosezione_aux = objNode.asText();
			    	Carenza carenza = new Carenza(getSezione(sottosezione_aux));
			    	JsonNode arrNode_sottosezione = nameNode.get("sottosezione_"+sottosezione_aux);
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
			    	String sottosezione_aux = objNode.asText();
			    	Carenza carenza = new Carenza(getSezione(sottosezione_aux));
			    	JsonNode arrNode_sottosezione = nameNode.get("sottosezione2_"+sottosezione_aux);
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
