package it.anac.segnalazioni.backend.report.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.report.model.Allegato;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.Segnalazione;

public abstract class ReportHelperJson {
	
	protected JsonNode nameNode;
	protected SimpleDateFormat dateformat;

	
	public ReportHelperJson(String json) throws JsonMappingException, JsonProcessingException
	{	
		ObjectMapper objectMapper = new ObjectMapper();
		
	    JsonNode jsonNode = objectMapper.readTree(json);
		this.nameNode = jsonNode.at("/data");
		
		System.out.println(nameNode.toPrettyString());
		
		this.dateformat = new SimpleDateFormat("dd-MM-yyyy");
	}
	
	protected String getValueFromJson(JsonNode nameNode, String prop)
	{
		String ret = "";
		if (nameNode!=null)
			if (nameNode.get(prop)!=null)
				ret = nameNode.get(prop).asText();
		return ret;
	}
	
	protected int getIntValueFromJson(JsonNode nameNode, String prop)
	{
		int ret_int = 0;
		String ret = getValueFromJson(nameNode, prop);
		if (!ret.trim().equals(""))
			Integer.valueOf(ret_int);		
		return ret_int;
	}
	
	protected Segnalante createSegnalanteFromJson()
	{
							
		Segnalante segnalante = new Segnalante(getValueFromJson(nameNode,"nome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"cognome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"codiceFiscale_soggetto_segnalante"),
											   getValueFromJson(nameNode,"qualifica"));		
		return segnalante;
		
	}
	
	protected Organizzazione createOrganizzazioneFromJson()
	{
							
		Organizzazione org = new Organizzazione(getValueFromJson(nameNode,"denominazione"),
				getValueFromJson(nameNode,"regione"),
				getValueFromJson(nameNode,"provincia"),
				getValueFromJson(nameNode,"comune"));

		String tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione");
		if (tipoEnte.trim().equals(""))
		tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione_rpct");
		org.setTipoEnte(tipoEnte);
		
		String mail =getValueFromJson(nameNode,"mail");
		if (mail.trim().equals(""))
		mail = getValueFromJson(nameNode,"mail_rpct");	
		org.setMail(mail);
		
		String pec = getValueFromJson(nameNode,"pec");
		if (pec.trim().equals(""))
		pec = getValueFromJson(nameNode,"pec_rpct");	
		org.setPec(pec);
		
		String telefono = getValueFromJson(nameNode,"telefono");
		if (telefono.trim().equals(""))
		telefono = getValueFromJson(nameNode,"telefono_rpct");	
		org.setTelefono(telefono);
		
		String cf = getValueFromJson(nameNode,"cf");
		if (telefono.trim().equals(""))
		telefono = getValueFromJson(nameNode,"cf_rpct");	
		org.setCodiceFiscale(cf);
		
		return org;
		
	}
	
	protected void chiusura(Segnalazione segnalazione)
	{
		// Aggiungiamo alcuni allegati
		segnalazione.addAllegato(new Allegato("contratto.pdf", "Contratto stipulato", "Documento contrattuale"));
		
		if (nameNode.get("soggettiaux").get("rpct").asBoolean())
			segnalazione.addAltroSoggetto("RPCT");
		if (nameNode.get("soggettiaux").get("procuraDellaRepubblica").asBoolean())
			segnalazione.addAltroSoggetto("Procura della Repubblica");
		if (nameNode.get("soggettiaux").get("procuraRegionaleCorteDeiConti").asBoolean())
			segnalazione.addAltroSoggetto("Procura Regionale Corte Dei Conti");
		if (nameNode.get("soggettiaux").get("ispettoratoPerLaFunzionePubblica").asBoolean())
			segnalazione.addAltroSoggetto("Ispettorato Per La Funzione Pubblica");
		if (nameNode.get("soggettiaux").get("prefettura").asBoolean())
			segnalazione.addAltroSoggetto("Prefettura");
		if (nameNode.get("soggettiaux").get("altro").asBoolean())
			segnalazione.addAltroSoggetto(getValueFromJson(nameNode, "altro_soggetto"));

				
		// Aggiungiamo Dati sensibili di cui il richiedente chiede esclusione da
		// pubblicazione
		segnalazione.setEsclusione(getValueFromJson(nameNode,"dati_sensibili"));
		
		System.out.println("CHIUSURA");
		System.out.println(nameNode.get("documenti_allegati_chiusura").toPrettyString());
						
	}
}
