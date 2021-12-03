package it.anac.segnalazioni.backend.report.model;

import java.util.ArrayList;
import java.util.List;

public class SubmissionMdl {
	private String nome;
	private String cognome;
	private String area;
	private List<SoggettoInteressato> incarichi;
	
	public SubmissionMdl(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.incarichi = new ArrayList<SoggettoInteressato>();
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void addSoggettoInteressato (SoggettoInteressato soggetto) {
		incarichi.add(soggetto);
	}
	public List<SoggettoInteressato> getSoggetti(){
		return incarichi;
	}

}
