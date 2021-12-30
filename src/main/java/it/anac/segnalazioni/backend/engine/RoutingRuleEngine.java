package it.anac.segnalazioni.backend.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RoutingRuleEngine {
	
	public final String UVMACT   = "ufficio1";
	public final String UVIF     = "ufficio1";
	public final String UVCP     = "ufficio1";
	public final String UVS      = "ufficio1";
	public final String UVLA     = "ufficio1";
	public final String UVSF     = "ufficio1";
	public final String NO_ROUTE = "ufficio1";
	
	private JsonNode nameNode;
	
	public RoutingRuleEngine(String json) throws JsonMappingException, JsonProcessingException
	{	
		ObjectMapper objectMapper = new ObjectMapper();
		
	    JsonNode jsonNode = objectMapper.readTree(json);
		this.nameNode = jsonNode.at("/data");
	}
	
	private String getValueFromJson(JsonNode nameNode, String prop)
	{
		String ret = "";
		if (nameNode!=null)
			if (nameNode.get(prop)!=null)
				ret = nameNode.get(prop).asText();
		return ret;
	}
	
	public String getRoute()
	{
		String ret = this.NO_ROUTE;
		
		String area = getValueFromJson(nameNode, "area");
		if (area.equals("anticorruzione"))
			return this.UVMACT;
		if (area.equals("trasparenza"))
			return this.UVMACT;
		if (area.equals("incarichi"))
			return this.UVIF;
		
		if (getValueFromJson(nameNode, "ambitodellintervento").equals("appalto1"))
		{
			if (getValueFromJson(nameNode,"contrattoSecretato").equals("si"))
				return this.UVS;
	
			
		} else if (getValueFromJson(nameNode, "ambitodellintervento").equals("appalto2"))
		{
			if (getValueFromJson(nameNode,"contrattoSecretato").equals("si"))
				return this.UVS;	
			
		}
		else
		{
		}
		
		return ret;
	}
}
