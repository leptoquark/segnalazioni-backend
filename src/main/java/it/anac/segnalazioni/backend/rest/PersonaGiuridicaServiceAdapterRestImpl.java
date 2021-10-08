package it.anac.segnalazioni.backend.rest;

import org.springframework.web.reactive.function.client.*;

import it.anac.segnalazioni.backend.domain.PersonaGiuridicaServiceAdapter;
import it.anac.segnalazioni.backend.model.pg.PersonaGiuridica;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonaGiuridicaServiceAdapterRestImpl implements PersonaGiuridicaServiceAdapter {
	
	@Value("${personagiuridica.ws.uri}")
	private String personaGiuridicaServiceUri;
	
	private WebClient webClient;
	
	public PersonaGiuridicaServiceAdapterRestImpl(WebClient.Builder webClientBuilder)
	{
		this.webClient = webClientBuilder.baseUrl(personaGiuridicaServiceUri).build();
	}

	@Override
	public PersonaGiuridica getPersonaGiuridicaFromDenominazioneLike(String denominazioneLike, int page, int size) {
		PersonaGiuridica pg = null;
		
		Mono<PersonaGiuridica> response = webClient
							.get()
							.uri(personaGiuridicaServiceUri+"/denominazione/{denominazioneLike}?page={page}&size={size}",
									denominazioneLike,
									page,
									size)
							.retrieve()
							.bodyToMono(PersonaGiuridica.class);
		
		try {
			pg = response.block();
		} catch(WebClientException e) 
		{
			e.printStackTrace();
		}
		return pg;
	}
	
}
