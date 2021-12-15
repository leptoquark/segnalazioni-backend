package it.anac.segnalazioni.backend.rest;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvException;

import it.anac.segnalazioni.backend.config.IstatCodes;
import it.anac.segnalazioni.backend.model.regione.Provincia;
import it.anac.segnalazioni.backend.model.regione.Regione;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils; 

@RestController
public class ComuniRestController
{
	@Autowired
	private ResourceLoader resourceLoader;
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/comuni")
	public String getComuni() throws IOException
	{
		 Resource resource = resourceLoader.getResource("classpath:jsondb/comuni.json");
		    InputStream inputStream = resource.getInputStream();

		        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
		        String data = new String(bdata, StandardCharsets.ISO_8859_1);

	     return data;
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/comuniFromProvincia")
	public List<String> getComuniFromProvincia(@RequestParam String provincia) throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		return ic.getComuniFromProvincia(provincia);
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/provinceFromRegione")
	public List<String> getProvinceFromRegione(@RequestParam String regione) throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		return ic.getProvinceFromRegione(regione);

	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/regioni")
	public List<String> getRegioni() throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		return ic.getRegioni();
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/regioneFromProvincia")
	public Regione getRegioneFromProvincia(String provincia) throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		Regione regione = new Regione();
		regione.nome = ic.getRegioneFromProvincia(provincia);
		return regione;
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/provinciaFromComune")
	public Provincia getProvinciaFromComune(String comune) throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		Provincia provincia = new Provincia();
		provincia.nome = ic.getProvinciaFromComune(comune);
		return provincia;
	}
}
