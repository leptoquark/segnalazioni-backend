package it.anac.segnalazioni.backend.report.util;

import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneRpct;
import it.anac.segnalazioni.backend.report.model.SoggettoInadempiente;

public class ReportHelperRpct extends ReportHelperJson {
	public ReportHelperRpct(String json) throws JsonMappingException, JsonProcessingException {
		super(json);
	}

	public SegnalazioneRpct createRpctFromJson() throws JsonMappingException, JsonProcessingException, ParseException
	{
		Segnalante segnalante = createSegnalanteFromJson();
		Organizzazione org = createOrganizzazioneFromJson(); 
		segnalante.setEnte(org);
		
		SegnalazioneRpct segnalazione = new SegnalazioneRpct(segnalante, new Date(), org,
			"£ATTIVITA_RPCT", "file_attestazione_OIV.pdf");
		
		segnalazione.addSoggetto(new SoggettoInadempiente("Vincenzo", "Bonetti", "BNTVNC58D10F501K", 5, 
				"Presidente consiglio di amministrazione", false, "vbonetti@pec.it", 
				"Via Minghetti", "10", "Italia", "RM", "Roma", "lorem vel metus commodo condimentum congue eget urna.", 
				true, "2019, 2020", false, ""));
		
		segnalazione.addSoggetto(new SoggettoInadempiente("Roberto", "Pozzaglia", "BNTVNC58D10F501K", 5, 
				"Presidente consiglio di amministrazione", dateformat.parse("07-01-2021"), "vbonetti@pec.it", 
				"Via Minghetti", "10", "Italia", "RM", "Roma", "lorem vel metus commodo condimentum congue eget urna.", 
				true, "2019, 2020", false, ""));

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
