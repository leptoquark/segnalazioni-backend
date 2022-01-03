package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.domain.AppaltiServiceAdapter;
import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.appalto.Appalto;
import it.anac.segnalazioni.backend.model.health.ResponseHealth;
import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;

@RestController
public class HealthRestController {
	
	@Autowired
	private AppaltiServiceAdapter appaltiService;
		
	public Appalto getAppaltoFromCIG(String cig) throws JsonMappingException, JsonProcessingException
	{
		// XA31927D46
		ObjectMapper om = new ObjectMapper();
		Appalto appalto = null;
		appalto = om.readValue(appaltiService.getAppaltoFromCIG(cig), Appalto.class);

		return appalto;
	}
	
	@Autowired
	private PersonaGiuridicaServiceAdapter personaGiuridicaService;
	
	public PersonaGiuridica[] getPGFromDenominazione(String denominazioneLike, int page, int size) throws JsonMappingException, JsonProcessingException 
	{
		ObjectMapper om = new ObjectMapper();
		PersonaGiuridica[] pg = null;

			pg = om.readValue(personaGiuridicaService.
					getPersonaGiuridicaFromDenominazioneLike(
							denominazioneLike,
							page,
							size), PersonaGiuridica[].class);
		
		return pg;
	}

   @CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
   @GetMapping("/health")
   ResponseHealth test() {
	   
	    ResponseHealth status = new ResponseHealth();
	    status.setStatusOK();
	   
	    String res_appalti;
		try {
			res_appalti = getAppaltoFromCIG("XA31927D46").codice_risposta.toString();
		} catch (JsonMappingException e) {
			res_appalti = "KO";
		} catch (JsonProcessingException e) {
			res_appalti = "KO";
		}
		   
		String res_pg;
		try {
			res_pg = getPGFromDenominazione("laziocrea", 0, 10)[0].id.toString();
		} catch (JsonMappingException e) {
			res_pg = "";
			
		} catch (JsonProcessingException e) {
			res_pg = "";
		}

		if (!res_appalti.equals("OK")) {
			   status.setMessage("Il Servizio CIG non risponde correttamente!");
			   status.setStatusKO();
		   } else
		   if(res_pg.equals("")) {
			   status.setMessage("Il Servizio Persona Giuridica non risponde correttamente!");
			   status.setStatusKO();
		   }
	   
	   return status;
   }
}