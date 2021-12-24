package it.anac.segnalazioni.backend.report.model;

/**
 * @author Giancarlo Carbone
 *
 * Responsabile Unico del Procedimento
 */
public class Rup extends Persona {
	
	public final static String RUP = "RUP";
	
	public Rup(String nome, String cognome) {
		super(nome, cognome);
		this.setQualifica(RUP);
	}
}
