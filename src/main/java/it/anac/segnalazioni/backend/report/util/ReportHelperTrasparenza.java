package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.anac.segnalazioni.backend.report.model.Carenza;
import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneTrasparenza;

public class ReportHelperTrasparenza extends ReportHelperJson {
	public ReportHelperTrasparenza(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneTrasparenza createTrasparenzaFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 
		segnalante.setEnte(org);
				
		SegnalazioneTrasparenza segnalazione = new SegnalazioneTrasparenza(segnalante, new Date(), org,
				"LINK_AMM_TRASP", "CARENZA");

		Carenza ca1 = new Carenza("Disposizioni generali");
		ca1.addSezione("Piano triennale per la prevenzione della corruzione e della trasparenza");
		ca1.addSezione("Atti generali");
		ca1.setContenutoObbligo("Aenean volutpat lorem vel metus commodo condimentum congue eget urna.");
		
		segnalazione.addCarenza(ca1);
		
		Carenza ca2 = new Carenza("Opere pubbliche");
		ca2.addSezione("Atti di programmazione delle opere pubbliche");
		ca2.addSezione("Tempi costi e indicatori di realizzazione delle opere pubbliche");
		ca2.setContenutoObbligo("Aenean volutpat lorem vel metus commodo condimentum congue eget urna.");
		
		segnalazione.addCarenza(ca2);
	
		// Oggetto della segnalazione NOTA: IN QUESTO CASO OGGETTO SEGNALAZIONE NON utilizzato
		// segnalazione.setOggetto(OGGETTO_SEGNALAZIONE);

		// Aggiungi tutti i possibili contenziosi
		segnalazione.setContenzioso(true);

		for (ContenziosoType ctype : ContenziosoType.values()) {
			Contenzioso cont = new Contenzioso(ctype);
			cont.setEstremi("ac viverra felis nunc ut ipsum. Nunc condimentum lacus");
			segnalazione.addContenzioso(cont);
		}

		// Aggiungi altre segnalazioni
		segnalazione.setAltreSegnalazioni(true);
		segnalazione.setEstremiSegnalazioni("lorem vel metus commodo condimentum congue eget urna.");
		
		chiusura(segnalazione);
		
		return segnalazione;
	}

}
