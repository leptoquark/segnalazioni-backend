package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Persona {
	private String nome;
	private String cognome;
	private String qualifica;
	
	public Persona(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.qualifica = "";
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

	@Override
    public String toString() {
        return "Persona{" +
                "nome='" + nome + "'" +
                ", cognome='" + cognome + "'" +
                ", qualifica='" + qualifica + "'" +
                "} " + super.toString();
    }
}
