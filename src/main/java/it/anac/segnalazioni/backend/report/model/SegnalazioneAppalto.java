package it.anac.segnalazioni.backend.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SegnalazioneAppalto extends Segnalazione {

	// CIG
	private String cig;
	
	// Stazione appaltante
	private Organizzazione sa;
	
	// Operatore economico
	private Organizzazione oe;
	
	// Elenco di ulteriori CIG
	private List<String> ulterioriCig;
	
	// Ambito d'intervento
	private String ambito;
	
	// Oggetto del contratto
	private String oggettoContratto;
	
	// Contratto secretato
	private boolean secretato = false;
	
	// Contraente generale
	private boolean generale = false;
	
	// La fornitura � destinata alla Stazione Appaltante
	// Questo campo � utilizzato per: Fornitura destinata alla centrale di committenza, al Soggetto Aggregatore o all Societ� in House
	private boolean perSe = false;
	
	// Importo base d'asta
	private int baseAsta;
	
	// Procedura di affidamento
	private String procedura;
	
	// Fase attuale della procedura
	private String fase;
	
	// Data riferita alla fase: data scadenza bando, data aggiudicazione contratto, data stipula contratto, data collaudo
	private Date dataFase;
	
	// Importo contrattuale
	private int importo;
	
	// RUP
	private Rup rup;
	
	// Data della presunta violazione
	private String dataViolazione;
	
	// Il segnalante � parte del giudizio
	private boolean parteGiudizio = false;
	
	public SegnalazioneAppalto(Segnalante segnalante, String oggetto, Date data, Organizzazione sa,
			String oggettoContratto, String ambito) {
		super(segnalante, data, "Appalto");
		this.setOggetto(oggetto);
		this.sa = sa;
		this.oggettoContratto = oggettoContratto;
		this.ambito = ambito;
		this.ulterioriCig = new ArrayList<String>();
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public Organizzazione getSa() {
		return sa;
	}

	public void setSa(Organizzazione sa) {
		this.sa = sa;
	}

	public Organizzazione getOe() {
		return oe;
	}

	public void setOe(Organizzazione oe) {
		this.oe = oe;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getOggettoContratto() {
		return oggettoContratto;
	}

	public void setOggettoContratto(String oggettoContratto) {
		this.oggettoContratto = oggettoContratto;
	}
	
	public boolean isSecretato() {
		return secretato;
	}

	public void setSecretato(boolean secretato) {
		this.secretato = secretato;
	}

	public boolean isGenerale() {
		return generale;
	}

	public void setGenerale(boolean generale) {
		this.generale = generale;
	}

	public boolean isPerSe() {
		return perSe;
	}

	public void setPerSe(boolean perSe) {
		this.perSe = perSe;
	}

	public List<String> getUlterioriCig() {
		return ulterioriCig;
	}

	public void setUlterioriCig(List<String> ulterioriCig) {
		this.ulterioriCig = ulterioriCig;
	}

	public int getBaseAsta() {
		return baseAsta;
	}

	public void setBaseAsta(int baseAsta) {
		this.baseAsta = baseAsta;
	}

	public String getProcedura() {
		return procedura;
	}

	public void setProcedura(String procedura) {
		this.procedura = procedura;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public Date getDataFase() {
		return dataFase;
	}

	public void setDataFase(Date dataFase) {
		this.dataFase = dataFase;
	}

	public int getImporto() {
		return importo;
	}

	public void setImporto(int importo) {
		this.importo = importo;
	}

	public Rup getRup() {
		return rup;
	}

	public void setRup(Rup rup) {
		this.rup = rup;
	}
	
	public String getDataViolazione() {
		return dataViolazione;
	}

	public void setDataViolazione(String dataViolazione) {
		this.dataViolazione = dataViolazione;
	}

	public boolean isParteGiudizio() {
		return parteGiudizio;
	}

	public void setParteGiudizio(boolean parteGiudizio) {
		this.parteGiudizio = parteGiudizio;
	}

	public void addCig(String cig) {
		this.ulterioriCig.add(cig);
	}
}
