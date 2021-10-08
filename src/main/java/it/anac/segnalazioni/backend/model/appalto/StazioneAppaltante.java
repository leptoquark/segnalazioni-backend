package it.anac.segnalazioni.backend.model.appalto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StazioneAppaltante{
	 @JsonProperty("INDIRIZZO") 
	 public String iNDIRIZZO;
	 @JsonProperty("ISTAT_COMUNE") 
	 public String iSTAT_COMUNE;
	 @JsonProperty("ID_CENTRO_COSTO") 
	 public String iD_CENTRO_COSTO;
	 @JsonProperty("DENOMINAZIONE_CENTRO_COSTO") 
	 public String dENOMINAZIONE_CENTRO_COSTO;
	 @JsonProperty("DENOMINAZIONE_AMMINISTRAZIONE_APPALTANTE") 
	 public String dENOMINAZIONE_AMMINISTRAZIONE_APPALTANTE;
	 @JsonProperty("CITTA") 
	 public String cITTA;
	 @JsonProperty("REGIONE") 
	 public String rEGIONE;
	 @JsonProperty("CF_AMMINISTRAZIONE_APPALTANTE") 
	 public String cF_AMMINISTRAZIONE_APPALTANTE;
	 @JsonProperty("SEZIONE_REGIONALE") 
	 public String sEZIONE_REGIONALE;
	 @JsonProperty("CODICE_AUSA") 
	 public String cODICE_AUSA;
}