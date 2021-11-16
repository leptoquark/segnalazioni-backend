package it.anac.segnalazioni.backend.domain;


public interface PersonaGiuridicaServiceAdapter {
	
	public String getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike, int page, int size);
	public String getPersonaGiuridicaFromCF(String cf);

}
