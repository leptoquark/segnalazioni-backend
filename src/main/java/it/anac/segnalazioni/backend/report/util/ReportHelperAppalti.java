package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Rup;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneAppalto;

public class ReportHelperAppalti extends ReportHelperJson {
	
	public ReportHelperAppalti(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneAppalto createAppaltoFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 

		segnalante.setEnte(org);

		Organizzazione sa = new Organizzazione(
				getValueFromJson(nameNode, "denominazione_sa"),
				getValueFromJson(nameNode, "regione_appalti"),
				getValueFromJson(nameNode, "provincia_appalti"),
				getValueFromJson(nameNode, "comune_appalti"));
		sa.setTipoEnte(getValueFromJson(nameNode, "stazioneappaltante"));
		
		
		SegnalazioneAppalto segnalazione = new SegnalazioneAppalto(
													segnalante,
													getValueFromJson(nameNode, "descrizione_intervento_segnalazione"), 
													new Date(),
													sa,
													"Fornitura di banchi a rotelle",
													"Appalto di Servizi/Forniture");

		// Operatore Economico
		Organizzazione oe = new Organizzazione(
				getValueFromJson(nameNode, "denominazione_operatoreEconomico"),
				getValueFromJson(nameNode, "codiceFiscale_operatoreEconomico"));
		segnalazione.setOe(oe);
		
		segnalazione.setCig(getValueFromJson(nameNode, "cig"));
		
		// Aggiungiamo ulteriori CIG
		JsonNode arrNode_cig = nameNode.get("cig_aggiuntivi");
		if (arrNode_cig.isArray()) {
		    for (JsonNode objNode : arrNode_cig) {
		        segnalazione.addCig(objNode.get("codiceIdentificativoGaraCig").asText());
		    }
		}
		
		// Secretato
		segnalazione.setSecretato(getValueFromJson(nameNode, "contrattoSecretato").equals("si"));
		segnalazione.setGenerale(getValueFromJson(nameNode, "contraenteGenerale").equals("si"));
		segnalazione.setPerSe(getValueFromJson(nameNode, "fornituraCentraleCommittenza").equals("si"));
		
		// Importo base d'asta
		segnalazione.setBaseAsta(getIntValueFromJson(nameNode, "importo_base_asta"));
		
		// Procedura di affidamento
		segnalazione.setProcedura(getValueFromJson(nameNode, "procedura_affidamento"));
		
		// Fase
		segnalazione.setFase(getValueFromJson(nameNode, "faseprocedura"));
		
		/*System.out.println("DATA: ");
		System.out.println(getValueFromJson(nameNode, "data_scadenza"));
		System.out.println(getValueFromJson(nameNode, "data_aggiudicazione"));
		System.out.println(getValueFromJson(nameNode, "data_stipula"));
		System.out.println(getValueFromJson(nameNode, "data_collaudo"));*/
		
		segnalazione.setDataFase(dateformat.parse("20-02-2021"));
		
		segnalazione.setImporto(getIntValueFromJson(nameNode, "importo_contrattuale"));
		
		// RUP
		segnalazione.setRup(new Rup(getValueFromJson(nameNode, "nome_rup"), getValueFromJson(nameNode, "cognome_rup")));
		
		// Data della presunta violazione
		segnalazione.setDataViolazione(getValueFromJson(nameNode, "PeriodoPresuntaSegnalazione"));
		
		// Aggiungi tutti i possibili contenziosi
		segnalazione.setContenzioso(getValueFromJson(nameNode, "contenziosoSegnalante").equals("true"));
		
		
		if (getValueFromJson(nameNode, "esistenzacivile").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.CIVILE);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzacivile_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzapenale").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.PENALE);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzapenale_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaanac").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.ANAC);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaanac_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzacorteconti").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.CORTE_CONTI);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzacorteconti_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaautotutela").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.AUTOTUTELA);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaautotutela_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaamministrativo").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.AMMINISTRATIVO);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaamministrativo_estremi"));
			segnalazione.addContenzioso(cont);
		}
		
		// Aggiungi altre segnalazioni
		segnalazione.setAltreSegnalazioni(getValueFromJson(nameNode, "esistenzaanacsegnalazioni").equals("true"));
		segnalazione.setEstremiSegnalazioni(getValueFromJson(nameNode, "esistenzaanacsegnalazioni_estremi"));
		
		chiusura(segnalazione);
		
		return segnalazione;
	}

}
