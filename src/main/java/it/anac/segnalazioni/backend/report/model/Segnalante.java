package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Segnalante {
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String qualifica;
	private Organizzazione ente;
	
	/**
	 * The default constructor.
	 */
	public Segnalante() {}
	
	public Segnalante (String nome, String cognome, String codiceFiscale, String qualifica) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.qualifica = qualifica;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getQualifica() {
		return qualifica;
	}
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
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
                ", nome='" + nome + "'" +
                ", cognome='" + cognome + "'" +
                ", qualifica='" + qualifica + "'" +
                "} " + super.toString();
    }
}


