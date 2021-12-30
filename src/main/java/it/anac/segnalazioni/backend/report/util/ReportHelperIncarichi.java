package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneIncarichi;
import it.anac.segnalazioni.backend.report.model.SoggettoInteressato;

public class ReportHelperIncarichi extends ReportHelperJson {
	public ReportHelperIncarichi(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneIncarichi createIncarichiFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 
		segnalante.setEnte(org);
		
		SegnalazioneIncarichi segnalazione = new SegnalazioneIncarichi(segnalante, new Date(), org,
				"Altro");

		segnalazione.addSoggetto(new SoggettoInteressato("Claudio", "Biancalana", "Birraio", true,
				dateformat.parse("07-11-2021"), "Panettiere", false, dateformat.parse("07-12-2021")));
		segnalazione
				.addSoggetto(new SoggettoInteressato("Claudio", "Biancalana", "Presidente", false, "Usciere", true));
		segnalazione.addSoggetto(new SoggettoInteressato("Claudio", "Biancalana", "Segretario", false,
				dateformat.parse("07-11-2021"), "Operativo", false, dateformat.parse("07-12-2021")));

		segnalazione.setDettaglio("Violazione articolo xyz");

		segnalazione.setOggetto("");

		segnalazione.setArtViolati("Art. 3 dl 190/2011");

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
