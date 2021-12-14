package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 */
public enum ContenziosoType {
	
	CIVILE("Procedimento Civile"), 
	PENALE("Procedimento Penale"), 
	ANAC("Altro procedimento/determinazione ANAC"), 
	CORTE_CONTI("Procedimento davanti alla Corte dei Conti"), 
	AUTOTUTELA("Procedimento amministrativo in autotutela"), 
	DISCIPLINARE("Procedimento disciplinare"), 
	AMMINISTRATIVO("Procedimento amministrativo");
	
	private String tipo;
	
	ContenziosoType(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
}
