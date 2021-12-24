package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class SegnalazioneTrasparenza extends Segnalazione {
	
	// Ente segnalato (obbligatorio)
	private Organizzazione ente;
	
	// Link sito web (obbligatorio)
	private String link;
	
	// Contenuto della segnalazione (obbligatorio)
	private String contenuto;
	
	// Carenze indicate dale segnalante (obbligatorio)
	private List<Carenza> carenze;
	
	public SegnalazioneTrasparenza(Segnalante segnalante, Date data, Organizzazione ente, String link, String contenuto) {
		super(segnalante, data, "Trasparenza");
		
		this.ente = ente;
		this.link = link;
		this.contenuto = contenuto;
		this.carenze = new ArrayList<Carenza>();

	}

	public Organizzazione getEnte() {
		return ente;
	}

	public void setEnte(Organizzazione enteSegnalato) {
		this.ente = enteSegnalato;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public List<Carenza> getCarenze() {
		return carenze;
	}

	public void setCarenze(List<Carenza> carenze) {
		this.carenze = carenze;
	}
	
	public void addCarenza(Carenza ca) {
		this.carenze.add(ca);
	}
}
