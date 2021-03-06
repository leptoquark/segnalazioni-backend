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

public class ReportHelperJson {
	
	protected JsonNode nameNode;
	protected SimpleDateFormat dateformat;
	
	protected final String ND = "N.D.";

	public ReportHelperJson(String json) throws JsonMappingException, JsonProcessingException
	{	
		ObjectMapper objectMapper = new ObjectMapper();
		
	    JsonNode jsonNode = objectMapper.readTree(json);
		this.nameNode = jsonNode.at("/data");
			
		this.dateformat = new SimpleDateFormat("dd-MM-yyyy");
	}
	
	public String getTipoSegnalazione()
	{
		return  getValueFromJson(this.nameNode,"area");		
	}
	
	protected String getValueFromJson(JsonNode nameNode, String prop)
	{
		return getValueFromJson(nameNode, prop, "");
	}
	
	private String getValueFromJson(JsonNode nameNode, String prop, String separator)
	{
		String ret = separator;
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
							
		Organizzazione org = new Organizzazione(getValueFromJson(nameNode,"denominazione",ND),
				getValueFromJson(nameNode,"regione",ND),
				getValueFromJson(nameNode,"provincia",ND),
				getValueFromJson(nameNode,"comune",ND));

		String tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione",ND);
		if (tipoEnte.trim().equals("") || tipoEnte.trim().equals(ND))
		tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione_rpct",ND);
		org.setTipoEnte(tipoEnte);
		
		String mail = getValueFromJson(nameNode,"mail",ND);
		if (mail.trim().equals("") || mail.trim().equals(ND))
		mail = getValueFromJson(nameNode,"mail_rpct");	
		org.setMail(mail);
		
		String pec = getValueFromJson(nameNode,"pec",ND);
		if (pec.trim().equals("") || pec.trim().equals(ND))
		pec = getValueFromJson(nameNode,"pec_rpct",ND);	
		org.setPec(pec);
		
		String telefono = getValueFromJson(nameNode,"telefono",ND);
		if (telefono.trim().equals("") || telefono.trim().equals(""))
		telefono = getValueFromJson(nameNode,"telefono_rpct",ND);	
		org.setTelefono(telefono);
		
		String cf = getValueFromJson(nameNode,"cf",ND);
		if (cf.trim().equals("") || cf.trim().equals(ND))
		cf = getValueFromJson(nameNode,"cf_rpct",ND);	
		org.setCodiceFiscale(cf);
		
		return org;
		
	}
	
	protected void chiusura(Segnalazione segnalazione)
	{	
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
		
		if (segnalazione.getAltriSoggetti().size()<=0)
			segnalazione.addAltroSoggetto("Nessuno");

		segnalazione.setEsclusione(getValueFromJson(nameNode,"dati_sensibili"));
		
		JsonNode arrNode_cig = nameNode.get("documenti_allegati_chiusura");
		if (arrNode_cig.isArray()) {
		    for (JsonNode objNode : arrNode_cig) {
		        String nome_doc = getValueFromJson(objNode.get("documento_allegati").get(0),"originalName");
		        Allegato allegato = new Allegato(nome_doc);
		        
		        String titolo_doc = getValueFromJson(objNode, "titolo_documento");
		        if (!titolo_doc.equals(""))
		        	allegato.setTitolo(titolo_doc);
		        
		        String note_doc = getValueFromJson(objNode,"note_documento");
		        if (!note_doc.equals(""))
		        	allegato.setDescrizione(note_doc);
	        	
				segnalazione.addAllegato(new Allegato(nome_doc, titolo_doc, note_doc));
		    }
		}					
	}
}
