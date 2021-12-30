package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Persona;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneCorruzione;

public class ReportHelperCorruzione extends ReportHelperJson {
	public ReportHelperCorruzione(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneCorruzione createCorruzioneFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 
		segnalante.setEnte(org);
		
		SegnalazioneCorruzione segnalazione = new SegnalazioneCorruzione(segnalante, new Date(), org,
				"Altro");

		segnalazione.setPersona(new Persona("Vincenzo", "Bonetti", "BNTVNC58D10F501K"));
		
		segnalazione.addDettaglio("Fenomeni di mala gestione e/o di cattiva amministrazione");
		segnalazione.addDettaglio("Fatti che contemplano rotazione");
		segnalazione.addDettaglio("Altro");
		
		segnalazione.setAltro("Aenean volutpat lorem vel metus commodo condimentum congue eget urna. Maecenas vehicula, ligula eget sodales ");
		
		// Oggetto della segnalazione
		segnalazione.setOggetto("");

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
