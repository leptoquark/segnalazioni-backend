package it.anac.segnalazioni.backend.domain;

import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;

public interface PersonaGiuridicaServiceAdapter {
	
	public PersonaGiuridica getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike, int page, int size);

}
