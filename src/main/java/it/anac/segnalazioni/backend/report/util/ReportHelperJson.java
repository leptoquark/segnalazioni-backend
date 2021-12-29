package it.anac.segnalazioni.backend.report.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ReportHelperJson {
	
	protected JsonNode nameNode;
	protected SimpleDateFormat dateformat;

	
	public ReportHelperJson(String json) throws JsonMappingException, JsonProcessingException
	{	
		ObjectMapper objectMapper = new ObjectMapper();
		
	    JsonNode jsonNode = objectMapper.readTree(json);
		this.nameNode = jsonNode.at("/data");
		
		this.dateformat = new SimpleDateFormat("dd-MM-yyyy");
	}
	
	protected String getValueFromJson(JsonNode nameNode, String prop)
	{
		String ret = "";
		
        if (nameNode.get(prop)!=null)
        	ret = nameNode.get(prop).asText();
        	
		return ret;
	}
	
	protected int getIntValueFromJson(JsonNode nameNode, String prop)
	{
		int ret_int = 0;
		String ret = getValueFromJson(nameNode, prop);
		if (!ret.trim().equals(""))
			Integer.valueOf(ret_int);		
		return ret_int;
	}

}
