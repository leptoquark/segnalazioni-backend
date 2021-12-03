package it.anac.segnalazioni.backend.report.model;

import java.util.List;

/**
 * @author Giancarlo Carbone
 *
 */
public class Segnalazione {
	private Segnalante segnalante;
	private String oggetto;
	private String data;
	private String area;
	private List<Allegato> allegati;
	private List<String> altriSoggetti;
	private String esclusione;
	
	public Segnalazione() {}
	
	public Segnalazione(Segnalante segnalante, String oggetto, String data, String area) {
		super();
		this.segnalante = segnalante;
		this.oggetto = oggetto;
		this.data = data;
		this.area = area;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
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
