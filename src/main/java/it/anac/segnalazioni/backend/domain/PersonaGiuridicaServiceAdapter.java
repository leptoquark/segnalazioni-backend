package it.anac.segnalazioni.backend.domain;


import it.anac.segnalazioni.backend.model.pg.PersoneGiuridiche;

public interface PersonaGiuridicaServiceAdapter {
	
	public PersoneGiuridiche getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike, int page, int size);

}
