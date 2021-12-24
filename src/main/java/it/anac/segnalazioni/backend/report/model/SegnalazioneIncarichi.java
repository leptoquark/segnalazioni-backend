package it.anac.segnalazioni.backend.report.model;
/**
 * @author Giancarlo Carbone
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SegnalazioneIncarichi extends Segnalazione {
	
	// Ente segnalato (obbligatorio)
	private Organizzazione ente;
	
	// Fattispecie (obbligatorio)
	private String fattispecie;
	
	// Dettaglio fattispecie
	private String dettaglio;
	
	// Violazione art. 3
	private boolean articolo3 = false;
	
	// Articoli violati
	private String artViolati;
	
	// Elenco soggetti interessati (obbligatorio)
	private List<SoggettoInteressato> soggetti;

	public SegnalazioneIncarichi(Segnalante segnalante, Date data, Organizzazione ente, String fattispecie) {
		super(segnalante, data, "Incarichi");
		
		this.ente = ente;
		this.fattispecie = fattispecie;
		
		this.soggetti = new ArrayList<SoggettoInteressato>();
	}

	public Organizzazione getEnte() {
		return ente;
	}

	public void setEnte(Organizzazione enteSegnalato) {
		this.ente = enteSegnalato;
	}

	public String getFattispecie() {
		return fattispecie;
	}

	public void setFattispecie(String fattispecie) {
		this.fattispecie = fattispecie;
	}

	public String getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}

	public boolean isArticolo3() {
		return articolo3;
	}

	public void setArticolo3(boolean articolo3) {
		this.articolo3 = articolo3;
	}

	public String getArtViolati() {
		return artViolati;
	}

	public void setArtViolati(String artViolati) {
		this.artViolati = artViolati;
	}
	
	public void addSoggetto(SoggettoInteressato soggetto) {
		this.soggetti.add(soggetto);
	}

	public List<SoggettoInteressato> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(List<SoggettoInteressato> soggetti) {
		this.soggetti = soggetti;
	}

}
