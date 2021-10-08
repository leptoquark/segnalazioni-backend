package it.anac.segnalazioni.backend.model.appalto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CONDIZIONINEGOZIATA{
	 @JsonProperty("ID_CONDIZIONE") 
	 public Object iD_CONDIZIONE;
	 @JsonProperty("DESCRIZIONE") 
	 public String dESCRIZIONE;
}
