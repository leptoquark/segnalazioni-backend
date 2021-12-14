package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public class Contenzioso {
	
	// Tipo di contenzioso
	private ContenziosoType tipo;
	
	// Estremi del contenzioso
	private String estremi;
	
	public Contenzioso(ContenziosoType tipo) {
		this.tipo = tipo;
	}

	public ContenziosoType getTipo() {
		return tipo;
	}

	public void setTipo(ContenziosoType tipo) {
		this.tipo = tipo;
	}

	public String getEstremi() {
		return estremi;
	}

	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}

}
