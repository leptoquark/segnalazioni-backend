package it.anac.segnalazioni.backend.model.pg;

import java.util.List;

//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

public class PersonaGiuridica {
 public String id;
 public DatiIdentificativi dati_identificativi;
 public String _check_ValPar;
 public String _source;
 public Documento documento;
 public List<String> _acl;
 public String ts;
 public TipoSoggetto tipoSoggetto;
 public Stato stato;
 public List<Classificazioni> classificazioni;
 public Ruoli ruoli;
 public List<Object> componenti;
}

