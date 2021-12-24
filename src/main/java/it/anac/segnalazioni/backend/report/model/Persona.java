package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Persona {
	
	// Nome della persona (obbligatorio)
	private String nome;
	
	// Cognome della persona (obbligatorio)
	private String cognome;
	
	// Qualifica della persona
	private String qualifica;
	
	// Codice fiscale della persona
	private String codiceFiscale;
	
	public Persona(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.qualifica = "";
	}
	
	public Persona(String nome, String cognome, String cf) {
		this(nome, cognome);
		this.codiceFiscale = cf;
	}
	
	public Persona(String nome, String cognome, String cf, String qualifica) {
		this(nome, cognome, cf);
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
	
    public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + "'" +
                ", cognome='" + cognome + "'" +
                ", qualifica='" + qualifica + "'" +
                "} " + super.toString();
    }
}
