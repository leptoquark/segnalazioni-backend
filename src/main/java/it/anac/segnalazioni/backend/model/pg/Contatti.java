package it.anac.segnalazioni.backend.model.pg;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contatti{
	 @JsonProperty("MAIL_PEC") 
	 public String mAIL_PEC;
	 @JsonProperty("EMAIL") 
	 public String eMAIL;
	 @JsonProperty("TELEFONO") 
	 public Object tELEFONO;
}