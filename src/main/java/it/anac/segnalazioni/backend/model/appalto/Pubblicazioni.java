package it.anac.segnalazioni.backend.model.appalto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pubblicazioni
{
	 @JsonProperty("DATA_CREAZIONE") 
	 public String dATA_CREAZIONE;
	 @JsonProperty("DATA_ALBO") 
	 public String dATA_ALBO;
	 @JsonProperty("DATA_GURI") 
	 public String dATA_GURI;
	 @JsonProperty("DATA_GUCE") 
	 public String dATA_GUCE;
	 @JsonProperty("DATA_PUBBLICAZIONE") 
	 public String dATA_PUBBLICAZIONE;
	 @JsonProperty("LINK_SITO_COMMITTENTE") 
	 public String lINK_SITO_COMMITTENTE;
	 @JsonProperty("DATA_BORE") 
	 public String dATA_BORE;
}