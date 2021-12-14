package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Segnalante extends Persona {
	private String codiceFiscale;
	private Organizzazione ente;
	
	public Segnalante (String nome, String cognome, String codiceFiscale, String qualifica) {
		super(nome, cognome);
		this.codiceFiscale = codiceFiscale;
		this.setQualifica(qualifica);
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public Organizzazione getEnte() {
		return ente;
	}
	public void setEnte(Organizzazione ente) {
		this.ente = ente;
	}
	
    @Override
    public String toString() {
        return "Segnalante{" +
                "codiceFiscale='" + codiceFiscale + "'" +
                "} " + super.toString();
    }
}


