package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import it.anac.segnalazioni.backend.report.model.Allegato;
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

	private Segnalante createSegnalanteFromJson()
	{
							
		Segnalante segnalante = new Segnalante(getValueFromJson(nameNode,"nome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"cognome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"codiceFiscale_soggetto_segnalante"),
											   getValueFromJson(nameNode,"qualifica"));		
		return segnalante;
		
	}
	
	private Organizzazione createOrganizzazioneFromJson()
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
													dateformat.parse("07-12-2021"),
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
		
		System.out.println("DATA: ");
		System.out.println(getValueFromJson(nameNode, "data_scadenza"));
		System.out.println(getValueFromJson(nameNode, "data_aggiudicazione"));
		System.out.println(getValueFromJson(nameNode, "data_stipula"));
		System.out.println(getValueFromJson(nameNode, "data_collaudo"));
		
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
		
		// Parametri di chiusura

		// Aggiungiamo alcuni allegati
		segnalazione.addAllegato(new Allegato("contratto.pdf", "Contratto stipulato", "Documento contrattuale"));
		segnalazione.addAllegato(new Allegato("lettera.pdf", "Lettera incarico"));
		segnalazione.addAllegato(new Allegato("documento.pdf", "Lettera incarico"));
		
		// Aggiungiamo altri soggetti interessati
		// segnalazione.addAltroSoggetto(new String("Nessuno"));
		segnalazione.addAltroSoggetto(new String("Procura della Repubblica"));
		segnalazione.addAltroSoggetto(new String("Prefettura"));

		// Aggiungiamo Dati sensibili di cui il richiedente chiede esclusione da
		// pubblicazione
		segnalazione
				.setEsclusione("Nunc at urna sit amet nunc rutrum cursus ut ac dolor. Nulla eleifend felis viverra, "
						+ "dapibus lectus sed, blandit velit. Nulla fringilla mi eu enim malesuada interdum. Duis non convallis mauris. "
						+ "Aenean volutpat lorem vel metus commodo condimentum congue eget urna. Maecenas vehicula, ligula eget sodales "
						+ "hendrerit, sapien sapien egestas diam, ac viverra felis nunc ut ipsum. Nunc condimentum lacus urna, vitae "
						+ "commodo mi gravida eget.");
		
		return segnalazione;
	}

}
