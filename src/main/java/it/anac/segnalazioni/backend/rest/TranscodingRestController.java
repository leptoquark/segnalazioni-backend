package it.anac.segnalazioni.backend.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvValidationException;

import it.anac.segnalazioni.backend.config.Transcoding;
import it.anac.segnalazioni.backend.model.transcodifica.TranscodificaResponseModel;


@RestController
@RequestMapping(path="/ws")
public class TranscodingRestController
{
	@Autowired
	private Transcoding transcoding;
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/transcode")
	public TranscodificaResponseModel transcode(String code) throws IOException, CsvValidationException
	{
		TranscodificaResponseModel trm = new TranscodificaResponseModel();
		trm.setCode(transcoding.valueFromCode(code));
		return trm;
	}
}
