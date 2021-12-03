package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Allegato {
	private String filename;
	private String titolo;
	private String descrizione;
	
	public Allegato() {}
	
	public Allegato(String filename, String titolo, String descrizione) {
		super();
		this.filename = filename;
		this.titolo = titolo;
		this.descrizione = descrizione;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Override
	public String toString() {
		 return  "Allegato{" +
	             "filename='" + filename + "'" +
	             "titolo='" + titolo + "'" +
	             "descrizione='" + descrizione + "'" +
	             "} " + super.toString();
	}
}
