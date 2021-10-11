package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;

@RestController
@RequestMapping(path="/ws")
public class PersonaGiuridicaRestController
{
	@Autowired
	private PersonaGiuridicaServiceAdapter personaGiuridicaService;
		
	@GetMapping("/personagiuridica/{denominazioneLike}?page={page}&pageSize={pageSize}")
	public String getPGFromDenominazione(@PathVariable String denominazioneLike,
										 @PathVariable int page,
										 @PathVariable int pageSize) 
	{
		return personaGiuridicaService.
				getPersonaGiuridicaFromDenominazioneLike(
					denominazioneLike,
					page,
					pageSize);
	}
}