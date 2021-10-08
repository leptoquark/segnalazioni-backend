package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.pg.PersoneGiuridiche;
import it.anac.segnalazioni.backend.model.protocollo.PersonaGiuridicaRequest;

@RestController
@RequestMapping(path="/ws")
public class PersonaGiuridicaRestController
{
	@Autowired
	private PersonaGiuridicaServiceAdapter personaGiuridicaService;
		
	@PostMapping("/personagiuridica/")
	public PersoneGiuridiche getPGFromDenominazione(@RequestBody PersonaGiuridicaRequest pgr) 
	{
		return personaGiuridicaService.
				getPersonaGiuridicaFromDenominazioneLike(
					pgr.getDenominazioneLike(),
					pgr.getPage(),
					pgr.getSize());
	}
}