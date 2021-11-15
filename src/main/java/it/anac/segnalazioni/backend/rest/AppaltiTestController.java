package it.anac.segnalazioni.backend.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppaltiTestController
{
	@Autowired
	private ResourceLoader resourceLoader;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/appalti-test")
	public String getAppalto(@RequestParam String cig) throws IOException
	{
		String data = "";
		
		if (cig.equals("1234567890"))
		{

		 Resource resource = resourceLoader.getResource("classpath:jsondb/appalti.json");
		    InputStream inputStream = resource.getInputStream();

		        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
		        data = new String(bdata, StandardCharsets.ISO_8859_1);
		}

	     return data;
	}
}
