package it.anac.segnalazioni.backend.model.appalto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Incaricati{
 @JsonProperty("CODICE_FISCALE") 
 public String cODICE_FISCALE;
 @JsonProperty("COGNOME") 
 public String cOGNOME;
 @JsonProperty("DESCRIZIONE_RUOLO") 
 public String dESCRIZIONE_RUOLO;
 @JsonProperty("NOME") 
 public String nOME;
 @JsonProperty("COD_RUOLO") 
 public int cOD_RUOLO;
}
