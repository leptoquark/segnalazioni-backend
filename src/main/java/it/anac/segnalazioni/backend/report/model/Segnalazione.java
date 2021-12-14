package it.anac.segnalazioni.backend.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Giancarlo Carbone
 *
 */
public class Segnalazione {
	// Persona fisica che effettua la segnalazione
	private Segnalante segnalante;
	
	// Oggetto della segnalazione
	private String oggetto;
	
	// Data della segnalazione
	private Date data;
	
	// Area della segnalazione
	private String area;
	
	// Documenti allegati
	private List<Allegato> allegati;
	
	// Altri soggetti interessati
	private List<String> altriSoggetti;
	
	// Esclusione dalla pubblicazione
	private String esclusione;
	
	// Esistenza di contenzioso sul medesimo oggetto
	private boolean contenzioso = false;
	
	// Contenziosi
	private List<Contenzioso> contenziosi;
	
	// Altre segnalazioni inviate ad ANAC
	private boolean altreSegnalazioni = false;
	
	// Estremi delle altre segnalazioni inviate ad ANAC
	private String estremiSegnalazioni;
	
	public Segnalazione(Segnalante segnalante, Date data, String area) {
		super();
		this.segnalante = segnalante;
		this.data = data;
		this.area = area;
		this.allegati = new ArrayList<Allegato>();
		this.altriSoggetti = new ArrayList<String>();
		this.contenziosi = new ArrayList<Contenzioso>(0);
	}

	public Segnalante getSegnalante() {
		return segnalante;
	}
	public void setSegnalante(Segnalante segnalante) {
		this.segnalante = segnalante;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getEsclusione() {
		return esclusione;
	}
	public void setEsclusione(String esclusione) {
		this.esclusione = esclusione;
	}
	
	public void addAllegato(Allegato allegato) {
		this.allegati.add(allegato);
	}
	public void addAltroSoggetto(String altroSoggetto) {
		this.altriSoggetti.add(altroSoggetto);
	}

	public List<Allegato> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<Allegato> allegati) {
		this.allegati = allegati;
	}
	public List<String> getAltriSoggetti() {
		return altriSoggetti;
	}
	public void setAltriSoggetti(List<String> altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}
	
	public boolean isContenzioso() {
		return contenzioso;
	}

	public void setContenzioso(boolean contenzioso) {
		this.contenzioso = contenzioso;
	}

	public List<Contenzioso> getContenziosi() {
		return contenziosi;
	}

	public void setContenziosi(List<Contenzioso> contenziosi) {
		this.contenziosi = contenziosi;
	}
	
	public void addContenzioso(Contenzioso c) {
		this.contenziosi.add(c);
	}

	public boolean isAltreSegnalazioni() {
		return altreSegnalazioni;
	}

	public void setAltreSegnalazioni(boolean altreSegnalazioni) {
		this.altreSegnalazioni = altreSegnalazioni;
	}

	public String getEstremiSegnalazioni() {
		return estremiSegnalazioni;
	}

	public void setEstremiSegnalazioni(String estremiSegnalazioni) {
		this.estremiSegnalazioni = estremiSegnalazioni;
	}	
	
	@Override
	public String toString() {
		 return  "Segnalazione{" +
	             "segnalante='" + segnalante.toString() + "'" +
				 "oggetto='" + oggetto + "'" +
	             "data='" + data + "'" + 
				 "area='" + area + "'" +
				 "} " + super.toString();
	}
}
