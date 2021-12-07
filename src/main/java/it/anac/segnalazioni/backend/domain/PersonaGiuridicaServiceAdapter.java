package it.anac.segnalazioni.backend.domain;

import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;

public interface PersonaGiuridicaServiceAdapter {
	
	public String getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike, int page, int size);
	public String getPersonaGiuridicaFromCF(String cf);
	public PersonaGiuridica[] getPersonaGiuridicaFromDenominazioneLikeAnd(String andString, int page, int size);

}
