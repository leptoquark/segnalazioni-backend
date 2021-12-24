package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
import java.util.Date;

/*
 * Soggetto coinvolto in una segnalazione per inconferibilit�/incompatibilit�
 */
public class SoggettoInteressato {
	
	// Nome del soggetto (obbligatorio)
	private String nome;
	
	// Cognome del soggetto (obbligatorio)
	private String cognome;
	
	// Incarico 1 (obbligatorio)
	private String incarico1;
	
	// Data assunzione incarico 1
	private Date assunzione1;
	
	// In corso di espeletamento (obbligatorio)
	private boolean inCorso1;
	
	// Incarico 2 (obbligatorio)
	private String incarico2;
	
	// Data assunzione incarico 2
	private Date assunzione2;
	
	// In corso di espeletamento (obbligatorio)
	private boolean inCorso2;
	
	public SoggettoInteressato(String nome, String cognome, String incarico1, boolean inCorso1, String incarico2, boolean inCorso2) {
		this.nome = nome;
		this.cognome = cognome;
		this.incarico1 = incarico1;
		this.inCorso1 = inCorso1;
		this.incarico2 = incarico2;
		this.inCorso2 = inCorso2;
	}
	
	public SoggettoInteressato(String nome, String cognome, String incarico1, boolean inCorso1, Date assunzione1,
			                                                String incarico2, boolean inCorso2, Date assunzione2) {
		this.nome = nome;
		this.cognome = cognome;
		this.incarico1 = incarico1;
		this.inCorso1 = inCorso1;
		this.incarico2 = incarico2;
		this.inCorso2 = inCorso2;
		this.assunzione1 = assunzione1;
		this.assunzione2 = assunzione2;
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

	public Date getAssunzione1() {
		return assunzione1;
	}

	public void setAssunzione1(Date assunzione1) {
		this.assunzione1 = assunzione1;
	}

	public boolean isInCorso1() {
		return inCorso1;
	}

	public void setInCorso1(boolean inCorso1) {
		this.inCorso1 = inCorso1;
	}

	public Date getAssunzione2() {
		return assunzione2;
	}

	public void setAssunzione2(Date assunzione2) {
		this.assunzione2 = assunzione2;
	}

	public boolean isInCorso2() {
		return inCorso2;
	}

	public void setInCorso2(boolean inCorso2) {
		this.inCorso2 = inCorso2;
	}
}
