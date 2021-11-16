package it.anac.segnalazioni.backend.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
@Scope("singleton")
public class IstatCodes {
	
	private HashMap<String,String> istat = new HashMap<String, String>();
	
	public IstatCodes() throws FileNotFoundException, IOException, CsvException
	{
		Resource resource = new ClassPathResource("jsondb/istat.csv");
		System.out.println("FILE:"+resource.getFile());
		FileReader fr = new FileReader(resource.getFile());
		try (CSVReader reader = new CSVReader(fr)) {
		      List<String[]> r = reader.readAll();
		      r.forEach(x -> istat.put(x[0],x[1]));
		  }
	}
	
	public String getComuneFromIstatCode(String istatCode)
	{
		return istat.get(istatCode);
	}
}
