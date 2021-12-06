package it.anac.segnalazioni.backend.rest;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvException;

import it.anac.segnalazioni.backend.config.IstatCodes;

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
	public String getRegioneFromProvincia(String provincia) throws IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		return ic.getRegioneFromProvincia(provincia);
	}
}
