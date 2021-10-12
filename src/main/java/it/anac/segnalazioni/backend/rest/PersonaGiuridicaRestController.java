package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;

@RestController
@RequestMapping(path="/ws")
public class PersonaGiuridicaRestController
{
	@Autowired
	private PersonaGiuridicaServiceAdapter personaGiuridicaService;
	
	@GetMapping("/personagiuridica/{denominazioneLike}")
	public PersonaGiuridica[] getPGFromDenominazione(@PathVariable String denominazioneLike,
										 @RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "10") int size) 
	{
		ObjectMapper om = new ObjectMapper();
		PersonaGiuridica[] pg = null;
		
		try {
			pg = om.readValue(personaGiuridicaService.
					getPersonaGiuridicaFromDenominazioneLike(
							denominazioneLike,
							page,
							size), PersonaGiuridica[].class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return pg;
	}
}