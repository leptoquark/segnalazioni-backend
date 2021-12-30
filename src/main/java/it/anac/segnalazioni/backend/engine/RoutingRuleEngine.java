package it.anac.segnalazioni.backend.engine;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RoutingRuleEngine {
	
	public final String UVMACT = "UVMACT";
	public final String UVIF   = "UVIF";
	public final String UVCP   = "UVCP";
	public final String UVS    = "UVS";
	public final String UVLA   = "UVLA";
	public final String UVSF   = "UVSF";
	
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
		String ret = "NO ROUTE";
		
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
