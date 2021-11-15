package it.anac.segnalazioni.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.domain.AppaltiServiceAdapter;
import it.anac.segnalazioni.backend.model.appalto.Appalto;

@RestController
@RequestMapping(path="/ws")
public class AppaltiRestController
{
	@Autowired
	private AppaltiServiceAdapter appaltiService;
		
	@CrossOrigin(origins = "http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local")
	@GetMapping("/appalti/{cig}")
	public Appalto getAppaltoFromCIG(@PathVariable String cig)
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
		
		/* solo per demo */
		if (appalto.stazione_appaltante.iSTAT_COMUNE.trim().equals("015065052"))
			appalto.stazione_appaltante.iSTAT_COMUNE="SALERNO";
		
		return appalto;
	}
}
