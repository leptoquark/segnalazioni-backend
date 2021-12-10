package it.anac.segnalazioni.backend.rest;

import org.springframework.web.reactive.function.client.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class PersonaGiuridicaServiceAdapterRestImpl implements PersonaGiuridicaServiceAdapter {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Value("${personagiuridica.ws.uri}")
	private String personaGiuridicaServiceUri;
	
	private WebClient webClient;
	
	public PersonaGiuridicaServiceAdapterRestImpl(WebClient.Builder webClientBuilder)
	{
		this.webClient = webClientBuilder.baseUrl(personaGiuridicaServiceUri).build();
	}
	
	@Override
	public String getPersonaGiuridicaFromCF(String cf) {
		
		String pg = null;
						
		Mono<String> response = webClient
							.get()
							.uri(personaGiuridicaServiceUri+"/codicefiscale/{cf}",
									cf)
							.retrieve()
							.bodyToMono(String.class);
		
		try {
			pg = response.block();
		} catch(WebClientException e) 
		{
			e.printStackTrace();
		}
		return pg;
	}	

	@Override
	public String getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike,
														   int page,
														   int size) {
		
		String pg = null;
				
		Mono<String> response = webClient
							.get()
							.uri(personaGiuridicaServiceUri+"/denominazione/{denominazioneLike}?page={page}&size={size}",
									denominazioneLike,
									page,
									size)
							.retrieve()
							.bodyToMono(String.class);
		
		try {
			pg = response.block();
		} catch(WebClientException e) 
		{
			e.printStackTrace();
		}
		return pg;
	}
		
	private Vector<String> getCleanString(String andString) throws IOException
	{
		 Resource resource = resourceLoader.getResource("classpath:jsondb/stopwords-iso.json");
		    InputStream inputStream = resource.getInputStream();

		  byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
		  String json = new String(bdata, StandardCharsets.ISO_8859_1);
		  
		  ObjectMapper objectMapper = new ObjectMapper();
		  JsonNode jsonNode = objectMapper.readTree(json);
		  JsonNode nameNode = jsonNode.get("it"); // seleziono italiano, migliorabile con I18N, se necessario

		  StringTokenizer st = new StringTokenizer(andString, " ");
		  
		  Vector<String> ret = new Vector<String>();
		  while (st.hasMoreTokens())
		  {
			  String aux = st.nextToken();
			  Iterator<?> iter = nameNode.iterator();
			  boolean found = false;
			  while(iter.hasNext())
			  {
				  String nextVal = iter.next().toString();
				  nextVal = nextVal.substring(1,nextVal.length()-1);
				  if (nextVal.equals(aux))
					  found = true;
			  }
			  if (!found)
				  ret.add(aux);
		  }
		  
		  return ret; 
	}

	@Override
	public PersonaGiuridica[] getPersonaGiuridicaFromDenominazioneLikeAnd(String andString,
																		  int page,
																		  int size) {
		
		ObjectMapper om = new ObjectMapper();
		PersonaGiuridica[] pg = null;
		PersonaGiuridica[] pgaux_all = null;

		try {
			Vector<String> iterate = getCleanString(andString);
			System.out.println("ITERATE: "+iterate.size());
			
			pgaux_all =
					om.readValue(
							getPersonaGiuridicaFromDenominazioneLike(
									andString,page,size),
							PersonaGiuridica[].class);
			
			for(int i=0; i<iterate.size(); i++)
			{
				PersonaGiuridica[] pgaux =
						om.readValue(
								getPersonaGiuridicaFromDenominazioneLike(
										iterate.get(i),page,size),
								PersonaGiuridica[].class);
				pg = (PersonaGiuridica[]) ArrayUtils.addAll(pg, pgaux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return  (PersonaGiuridica[]) ArrayUtils.addAll(pgaux_all,pg);
	}
}