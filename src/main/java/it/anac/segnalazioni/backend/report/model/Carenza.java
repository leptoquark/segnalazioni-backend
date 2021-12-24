package it.anac.segnalazioni.backend.report.model;


/**
 * @author Giancarlo Carbone
 *
 */
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * Rappresenta la carenza indicata dal segnalate relativa ad una sezione di Amministrazione/Societ� Trasparente
 * Soltanto il campo "sezione" � obbligatorio
 *
 */

public class Carenza {
	
	// Sezione oggetto di segnalazione
	private String sezione;
	
	// Sotto sezioni
	private List<String> sottoSezioni;
	
	// Descrizione del contenuto dell'obbligo
	private String contenutoObbligo;

	public Carenza(String sezione) {
		super();
		this.sezione = sezione;
		this.sottoSezioni = new ArrayList<String>();
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public List<String> getSottoSezioni() {
		return sottoSezioni;
	}

	public void setSottoSezioni(List<String> sottoSezioni) {
		this.sottoSezioni = sottoSezioni;
	}

	public String getContenutoObbligo() {
		return contenutoObbligo;
	}

	public void setContenutoObbligo(String contenutoObbligo) {
		this.contenutoObbligo = contenutoObbligo;
	}
	
	public void addSezione(String sottoSezione) {
		this.sottoSezioni.add(sottoSezione);
	}

}
