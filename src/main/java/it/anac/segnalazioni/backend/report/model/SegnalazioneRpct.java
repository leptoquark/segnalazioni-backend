package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class SegnalazioneRpct extends Segnalazione {
	
	// Ente segnalato (obbligatorio)
	private Organizzazione ente;
	
	// Attivit� svolta da RPCT per assicurare adempimento obbligo (obbligatorio)
	private String attivita;
	
	// Filename attestazione OIV allegata (obbligatorio)
	private String fileOiv;
	
	// Soggetti inadempienti (obbligatorio)
	private List<SoggettoInadempiente> soggetti;
	
	public SegnalazioneRpct(Segnalante segnalante, Date data, Organizzazione ente, String attivita, String fileOiv) {
		super(segnalante, data, "RPCT");
		
		this.ente = ente;
		this.attivita = attivita;
		this.fileOiv = fileOiv;
		this.soggetti = new ArrayList<SoggettoInadempiente>();
	}

	public Organizzazione getEnte() {
		return ente;
	}

	public void setEnte(Organizzazione enteSegnalato) {
		this.ente = enteSegnalato;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	public String getFileOiv() {
		return fileOiv;
	}

	public void setFileOiv(String fileOiv) {
		this.fileOiv = fileOiv;
	}

	public List<SoggettoInadempiente> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(List<SoggettoInadempiente> soggetti) {
		this.soggetti = soggetti;
	}
	
	public void addSoggetto(SoggettoInadempiente soggetto) {
		this.soggetti.add(soggetto);
	}
}
