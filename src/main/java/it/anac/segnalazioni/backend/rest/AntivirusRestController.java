package it.anac.segnalazioni.backend.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anac.segnalazioni.backend.domain.AntivirusServiceAdapter;
import it.anac.segnalazioni.backend.domain.antivirus.AntivirusRequest;


@RestController
@RequestMapping(path="/ws")
public class AntivirusRestController
{
	@Autowired
	private AntivirusServiceAdapter antivirusService;
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@PostMapping("/antivirus")
	public boolean checkVirusOnUrl(@RequestBody AntivirusRequest req) throws IOException
	{
		return antivirusService.checkVirusOnUrl(req.getUrl());
	}
}
