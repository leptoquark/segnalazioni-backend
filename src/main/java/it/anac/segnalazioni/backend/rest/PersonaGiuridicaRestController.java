package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local",
							"http://localhost:4200"})
	@GetMapping("/personagiuridica/denominazione")
	public PersonaGiuridica[] getPGFromDenominazione(@RequestParam String denominazioneLike,
										 @RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "100") int size) 
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
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local",
							"http://localhost:4200"})
	@GetMapping("/personagiuridica/cf")
	public PersonaGiuridica getPGFromCF(@RequestParam String cf) 
	{
		ObjectMapper om = new ObjectMapper();
		PersonaGiuridica pg = new PersonaGiuridica();
		
		try {
			pg = om.readValue(personaGiuridicaService.
					getPersonaGiuridicaFromCF(cf),
					PersonaGiuridica.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return pg;
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local",
							"http://localhost:4200"})
	@GetMapping("/personagiuridica/denominazione-and")
	public PersonaGiuridica[] getPGFromDenominazioneAnd(@RequestParam String denominazioneLike,
										 @RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "10") int size) 
	{
		PersonaGiuridica[] pg = null;

		pg = personaGiuridicaService.
				getPersonaGiuridicaFromDenominazioneLikeAnd(
						denominazioneLike,
						page,
						size);
	
		return pg;
	}

}