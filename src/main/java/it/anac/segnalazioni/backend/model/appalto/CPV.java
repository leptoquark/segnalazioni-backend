package it.anac.segnalazioni.backend.model.appalto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CPV{
	 @JsonProperty("DESCRIZIONE_CPV") 
	 public String dESCRIZIONE_CPV;
	 @JsonProperty("COD_CPV") 
	 public String cOD_CPV;
	 @JsonProperty("FLAG_PREVALENTE") 
	 public Object fLAG_PREVALENTE;
}