package it.anac.segnalazioni.backend.model.appalto;

import java.util.List;

/* ObjectMapper om = new ObjectMapper();
Appalto appalto = om.readValue(myJsonString), Appalto.class); */

public class Appalto
{
	public String codice_risposta;
	public StazioneAppaltante stazione_appaltante;
	public Pubblicazioni pubblicazioni;
	public Bando bando;
	public List<Incaricati> incaricati;
}

