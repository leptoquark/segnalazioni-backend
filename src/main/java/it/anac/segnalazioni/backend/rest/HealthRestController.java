package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.domain.AppaltiServiceAdapter;
import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.appalto.Appalto;
import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;

@RestController
public class HealthRestController {
	
	@Autowired
	private AppaltiServiceAdapter appaltiService;
		
	public Appalto getAppaltoFromCIG(String cig)
	{
		// XA31927D46
		ObjectMapper om = new ObjectMapper();
		Appalto appalto = null;
		try {
			appalto = om.readValue(appaltiService.getAppaltoFromCIG(cig), Appalto.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return appalto;
	}
	
	@Autowired
	private PersonaGiuridicaServiceAdapter personaGiuridicaService;
	
	public PersonaGiuridica[] getPGFromDenominazione(String denominazioneLike, int page, int size) 
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

   @GetMapping("/health")
   HttpStatus test() {
	   
	   HttpStatus ret = null;
	   
	   if (getAppaltoFromCIG("XA31927D46").codice_risposta.toString().equals("OK") &&
			   !getPGFromDenominazione("laziocrea", 0, 10)[0].id.toString().equals(""))
		   ret =  HttpStatus.ACCEPTED;
       else
    	   ret = HttpStatus.INTERNAL_SERVER_ERROR;
	   
	   return ret;
   }

}