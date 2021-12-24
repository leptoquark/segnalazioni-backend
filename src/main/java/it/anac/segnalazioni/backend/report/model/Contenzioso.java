package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */

/**
 * Esistenza di contenzioso/procedimento amministrativo/civile/contabile sul medesimo oggetto a conoscenza del segnalante
 * 
 */
public class Contenzioso {
	
	// Tipo di contenzioso (obbligatorio)
	private ContenziosoType tipo;
	
	// Estremi del contenzioso
	private String estremi;
	
	public Contenzioso(ContenziosoType tipo) {
		this.tipo = tipo;
	}
	
	public Contenzioso(ContenziosoType tipo, String estremi) {
		this.tipo = tipo;
		this.estremi = estremi;
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
