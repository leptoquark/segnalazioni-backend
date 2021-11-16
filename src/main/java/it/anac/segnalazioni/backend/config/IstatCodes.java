package it.anac.segnalazioni.backend.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@Service
@Scope("singleton")
public class IstatCodes {
	
	
	private HashMap<String,String> istat = new HashMap<String, String>();
	
	public IstatCodes() throws FileNotFoundException, IOException, CsvException
	{
		FileReader fr = new FileReader(ResourceUtils.getFile("classpath:istat.csv"));	
		CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		  try(CSVReader reader = new CSVReaderBuilder(fr)
		          .withCSVParser(csvParser)
		          .build()){
		      List<String[]> r = reader.readAll();
		      r.forEach(x -> istat.put(x[0],x[1]));
		  }
	}
	
	public String getComuneFromIstatCode(String istatCode)
	{
		return istat.get(istatCode);
	}
}
