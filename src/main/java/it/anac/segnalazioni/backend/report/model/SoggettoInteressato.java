package it.anac.segnalazioni.backend.report.model;

public class SoggettoInteressato {
	private String nome;
	private String cognome;
	private String incarico1;
	private String incarico2;
	
	public SoggettoInteressato(String nome, String cognome, String incarico1, String incarico2) {
		this.nome = nome;
		this.cognome = cognome;
		this.incarico1 = incarico1;
		this.incarico2 = incarico2;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getIncarico2() {
		return incarico2;
	}

	public void setIncarico2(String incarico2) {
		this.incarico2 = incarico2;
	}

	public String getIncarico1() {
		return incarico1;
	}
	public void setIncarico1(String incarico) {
		this.incarico1 = incarico;
	}

}
