package it.anac.segnalazioni.backend.rest;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anac.segnalazioni.backend.domain.ProtocolloService;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;

@RestController
@RequestMapping(path="/ws")
public class ProtocolloRestController
{
	@Autowired
	private ProtocolloService protocolloService;
		
	@PostMapping("/protocollo")
	public String invio(@RequestBody ProtocolloRequest pr) 
	{
		String ret = "";
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
						pr.getDocumentoTipoDocumento(),
						pr.getDocumentoNomeFile(),
						pr.getDocumentoUrlDocumento());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
