package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.time.Instant;
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
	
	private String getAmbitoIntervento(String val)
	{
		String ret = "N.D.";
		if (val.equals("appalto1"))
			ret = "Appalto di Lavori";
		else if (val.equals("appalto2"))
			ret = "Appalto di Servizi/Forniture";
		else if (val.equals("serviziDiIngegneriaEdArchitettura"))
			ret = "Servizi di ingegneria ed architettura";
		else if (val.equals("partenariatoPubblicoPrivato"))
			ret = "Parternariato Pubblico Privato";
		else if (val.equals("concessioneDiLavori"))
			ret = "Concessione di Lavori";
		else if (val.equals("concessioneDiServiziForniture"))
			ret = "Concessione di Servizi / Forniture";
		else if (val.equals("appaltoDiServiziPubbliciLocali"))
			ret = "Appalto di Servizi Pubblici Locali";
		else if (val.equals("concessioneDiServiziPubbliciLocali"))
			ret = "Concessione di Servizi Pubblici Locali";
		return ret;
	}
	
	private String getProceduraAffidamento(String val)
	{
		String ret = "N.D.";
		if (val.equals("accordoQuadro"))
			ret = "Accordo quadro";
		else if (val.equals("affidamentoASocietaInHouse"))
			ret = "Affidamento a società In House";
		else if (val.equals("affidamentoDirettoRiservatoOInEconomia"))
			ret = "Affidamento diretto, riservato o in economia";
		else if (val.equals("dialogoCompetitivo"))
			ret = "Dialogo competitivo";
		else if (val.equals("partenariato"))
			ret = "Partenariato Pubblico Privato";
		else if (val.equals("proceduraAperta"))
			ret = "Procedura Aperta";
		else if (val.equals("proceduraNegoziata"))
			ret = "Procedura Negoziata";
		else if (val.equals("proceduraRistretta"))
			ret = "Procedura Ristretta";
		else if (val.equals("sistemaDinamicoDiAcquisizione"))
			ret = "Sistema Dinamico di Acquisizione";
		else if (val.equals("altroTipoDiProcedura"))
			ret = "Altro tipo di procedura";
		return ret;	
	}
	
	private String getFaseProcedura(String val)
	{
		String ret = "N.D.";
		if (val.equals("programmazioneProgettazione"))
			ret = "Programmazione/Progettazione";
		else if (val.equals("pubblicazioneDelBando"))
			ret = "Pubblicazione del Bando";
		else if (val.equals("garaInCorso"))
			ret = "Gara in Corso";
		else if (val.equals("gara"))
			ret = "Gara conclusa o aggiudicata";
		else if (val.equals("contratto"))
			ret = "Esecuzione del Contratto";
		else if (val.equals("collaudo"))
			ret = "Collaudo/Veririca di conformità";
		else if (val.equals("proroga"))
			ret = "Proroga";
				
		return ret;
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
													getValueFromJson(nameNode, "oggettoContratto_sa"),
													getAmbitoIntervento(getValueFromJson(nameNode, "ambitodellintervento")));

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
		segnalazione.setProcedura(getProceduraAffidamento(getValueFromJson(nameNode, "procedura_affidamento")));
		
		// Fase
		segnalazione.setFase(getFaseProcedura(getValueFromJson(nameNode, "faseprocedura")));
		

		String dataFaseAux = getValueFromJson(nameNode.get("data_scadenza"),"$date");
		if (dataFaseAux.trim().equals("")) dataFaseAux = getValueFromJson(nameNode.get("data_aggiudicazione"),"$date");
		if (dataFaseAux.trim().equals("")) dataFaseAux = getValueFromJson(nameNode.get("data_stipula"),"$date");
		if (dataFaseAux.trim().equals("")) dataFaseAux = getValueFromJson(nameNode.get("data_collaudo"),"$date");
		
		
		if (!dataFaseAux.trim().equals(""))
		{
			Instant instant = Instant.parse(dataFaseAux);
			Date dataFase = Date.from(instant);
			segnalazione.setDataFase(dataFase);
		}
		
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