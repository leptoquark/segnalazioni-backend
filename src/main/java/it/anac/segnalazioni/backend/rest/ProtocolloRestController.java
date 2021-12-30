package it.anac.segnalazioni.backend.rest;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloResponse;

@RestController
@RequestMapping(path="/ws")
public class ProtocolloRestController
{
	@Autowired
	private ProtocolloService protocolloService;
		
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@PostMapping("/protocollo")
	public ProtocolloResponse invio(@RequestBody ProtocolloRequest pr) throws IOException 
	{
		ProtocolloResponse ret = new ProtocolloResponse();
		try {
			 ret = 
				protocolloService.invio(
						pr.getIdentificazioneAoo(),
						pr.getIdentificazioneUfficio(),
						pr.getProtocolloTipoProtocollo(),
						pr.getProtocolloOggetto(),
						pr.getProtocolloMittente(),
						pr.getProtcolloTipoDocumento(),
						pr.getAssegnatarioUfficio(),
						pr.getAssegnatarioCompetenza(),
						pr.getFileDocuments());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
