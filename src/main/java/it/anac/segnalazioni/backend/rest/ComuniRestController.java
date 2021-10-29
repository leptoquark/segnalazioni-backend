package it.anac.segnalazioni.backend.rest;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils; 

@RestController
public class ComuniRestController
{
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping("/comuni")
	public String getComuni() throws IOException
	{

		 Resource resource = resourceLoader.getResource("classpath:jsondb/comuni.json");
		    InputStream inputStream = resource.getInputStream();

		        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
		        String data = new String(bdata, StandardCharsets.ISO_8859_1);

	     return data;
	}
}
