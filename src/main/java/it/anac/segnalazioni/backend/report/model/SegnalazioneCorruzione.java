package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SegnalazioneCorruzione extends Segnalazione {
	
	// Ente segnalato (obbligatorio)
	private Organizzazione ente;
	
	// Persona fisica oggetto della segnalazione
	private Persona persona;
	
	// Questione che attiene alla segnalazione (obbligatorio)
	private String questione;
	
	// Dettagli sulla questione che attiene alla segnalazione
	private List<String> dettagli;
	
	// Dettagli sulla questione in caso di Altro
	private String altro;

	public SegnalazioneCorruzione(Segnalante segnalante, Date data, Organizzazione ente, String questione) {
		super(segnalante, data, "Anticorruzione");
		
		this.ente = ente;
		this.questione = questione;
		this.dettagli = new ArrayList<String>();
	}

	public Organizzazione getEnte() {
		return ente;
	}

	public void setEnte(Organizzazione enteSegnalato) {
		this.ente = enteSegnalato;
	}

	public String getQuestione() {
		return questione;
	}

	public void setQuestione(String questione) {
		this.questione = questione;
	}
	
	public void addDettaglio(String dettaglio) {
		this.dettagli.add(dettaglio);
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<String> getDettagli() {
		return dettagli;
	}

	public void setDettagli(List<String> dettagli) {
		this.dettagli = dettagli;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

}
