package it.anac.segnalazioni.backend.rest;

import org.springframework.web.reactive.function.client.*;

import it.anac.segnalazioni.backend.domain.AppaltiServiceAdapter;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppaltiServiceAdapterRestImpl implements AppaltiServiceAdapter {
	
	@Value("${appalti.ws.uri}")
	private String appaltiServiceUri;
	
	private WebClient webClient;
	
	public AppaltiServiceAdapterRestImpl(WebClient.Builder webClientBuilder)
	{
		this.webClient = webClientBuilder.baseUrl(appaltiServiceUri).build();
	}

	@Override
	public String getAppaltoFromCIG(String cig) {
		String ret = "";
		
		Mono<String> response = webClient
							.get()
							.uri(appaltiServiceUri+"/getSmartCig/{cig}", cig)
							.retrieve()
							.bodyToMono(String.class);
		
		try {
			ret = response.block();
		} catch(WebClientException e) 
		{
			// La chiamata REST fallisce
			e.printStackTrace();
		}
		return ret;
	}
	
}
