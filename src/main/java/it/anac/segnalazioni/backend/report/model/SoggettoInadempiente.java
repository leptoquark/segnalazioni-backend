package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
import java.util.Date;

/*
 * Soggetto inadenpiente in caso di segnalazione da parte di un RPCT
 */
public class SoggettoInadempiente extends Persona {
	
	// Anni duranta del mandato (obbligatorio)
	private int mandato;
	
	// Cessato dal servizio (obbligatorio)
	private boolean cessato;
	
	// Data cessazione (obbligatorio)
	private Date dataCessato;
	
	// Indirizzo PEC personale (obbligatorio)
	private String pec;
	
	// Via/Piazza (obbligatorio)
	private String indirizzo;
	
	// Numero civico (obbligatorio)
	private String civico;
	
	// Nazione (obbligatorio)
	private String nazione;
	
	// Provincia (obbligatorio)
	private String provincia;
	
	// Comune (obbligatorio)
	private String comune;
	
	// Obblighi disattesi (obbligatorio)
	private String obblighi;
	
	// Dichiarazione patrimoniale (obbligatorio)
	private boolean patrimoniale;
	
	// Annualit� diattese della dichiarazione patrimoniale (obbligatorio)
	private String anniPatrimoniale;
	
	// Dichiarazione reddituale (obbligatorio)
	private boolean reddituale;
	
	// Annualit� diattese della dichiarazione reddituale (obbligatorio)
	private String anniReddituale;

	public SoggettoInadempiente(String nome, String cognome, String cf, int mandato, String qualifica, 
			boolean cessato, String pec, String indirizzo, String civico, String nazione, String provincia, String comune,
			String obblighi, boolean patrimoniale, String anniPatrimoniale, boolean reddituale, String anniReddituale) {
		super(nome, cognome, cf);
		this.setQualifica(qualifica);
		this.mandato = mandato;
		this.cessato = cessato;
		this.pec = pec;
		this.indirizzo = indirizzo;
		this.civico = civico;
		this.nazione = nazione;
		this.provincia = provincia;
		this.comune = comune;
		this.obblighi = obblighi;
		this.patrimoniale = patrimoniale;
		this.anniPatrimoniale = anniPatrimoniale;
		this.reddituale = reddituale;
		this.anniReddituale = anniReddituale;

	}
	
	public SoggettoInadempiente(String nome, String cognome, String cf, int mandato, String qualifica, 
			Date dataCessato, String pec, String indirizzo, String civico, String nazione, String provincia, String comune,
			String obblighi, boolean patrimoniale, String anniPatrimoniale, boolean reddituale, String anniReddituale) {
		
		super(nome, cognome, cf);
		this.setQualifica(qualifica);
		this.mandato = mandato;
		this.dataCessato = dataCessato;
		this.cessato = true;
		this.pec = pec;
		this.indirizzo = indirizzo;
		this.civico = civico;
		this.nazione = nazione;
		this.provincia = provincia;
		this.comune = comune;
		this.obblighi = obblighi;
		this.patrimoniale = patrimoniale;
		this.anniPatrimoniale = anniPatrimoniale;
		this.reddituale = reddituale;
		this.anniReddituale = anniReddituale;

	}

	public int getMandato() {
		return mandato;
	}

	public void setMandato(int mandato) {
		this.mandato = mandato;
	}

	public boolean isCessato() {
		return cessato;
	}

	public void setCessato(boolean cessato) {
		this.cessato = cessato;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getObblighi() {
		return obblighi;
	}

	public void setObblighi(String obblighi) {
		this.obblighi = obblighi;
	}

	public boolean isPatrimoniale() {
		return patrimoniale;
	}

	public void setPatrimoniale(boolean patrimoniale) {
		this.patrimoniale = patrimoniale;
	}

	public String getAnniPatrimoniale() {
		return anniPatrimoniale;
	}

	public void setAnniPatrimoniale(String anniPatrimoniale) {
		this.anniPatrimoniale = anniPatrimoniale;
	}

	public boolean isReddituale() {
		return reddituale;
	}

	public void setReddituale(boolean reddituale) {
		this.reddituale = reddituale;
	}

	public String getAnniReddituale() {
		return anniReddituale;
	}

	public void setAnniReddituale(String anniReddituale) {
		this.anniReddituale = anniReddituale;
	}

	public Date getDataCessato() {
		return dataCessato;
	}

	public void setDataCessato(Date dataCessato) {
		this.dataCessato = dataCessato;
	}

}
