package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Organizzazione {
	
	// Denominazione Amministrazione/Ente (obbligatorio)
	private String denominazione;
	
	// Codice fiscale
	private String codiceFiscale;
	
	// Tipologia Amministrazione/Ente
	private String tipoEnte;
	
	// Regione (obbligatorio)
	private String regione;
	
	// Provincia (obbligatorio)
	private String provincia;
	
	// Comune (obbligatorio)
	private String comune;
	
	// Indirizzo mail
	private String mail;
	
	// Indirizzo PEC
	private String pec;
	
	// Numero di telefono
	private String telefono;
	
	public Organizzazione(String denominazione, String codiceFiscale) {
		super();
		this.denominazione = denominazione;
		this.codiceFiscale = codiceFiscale;
	}
	
	public Organizzazione(String denominazione, String regione, String provincia, String comune) {
		super();
		this.denominazione = denominazione;
		this.regione = regione;
		this.provincia = provincia;
		this.comune = comune;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Override
	public String toString() {
		 return  "Organizzazione{" +
				 "denominazione='" + denominazione + "'" +
				 "regione='" + regione + "'" +
				 "provincia='" + provincia + "'" +
				 "comune='" + comune + "'"  + 
				 "} " + super.toString();
	 }
	

}
