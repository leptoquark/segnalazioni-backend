package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 * 
 * Persona che effettua la segnalazione
 *
 */
public class Segnalante extends Persona {
	
	// Amministrazione/ente dove il segnalante esercita la qualifica (opzionale)
	private Organizzazione ente;
	
	public Segnalante (String nome, String cognome, String codiceFiscale, String qualifica) {
		super(nome, cognome, codiceFiscale, qualifica);
	}
	
	public Segnalante (String nome, String cognome, String codiceFiscale, String qualifica, Organizzazione ente) {
		super(nome, cognome, codiceFiscale, qualifica);
		this.ente = ente;
	}
	
	public Organizzazione getEnte() {
		return ente;
	}
	public void setEnte(Organizzazione ente) {
		this.ente = ente;
	}
	
}


