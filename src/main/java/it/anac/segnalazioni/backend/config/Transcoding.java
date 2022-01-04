package it.anac.segnalazioni.backend.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class Transcoding {

	private FileReader reader;
	private CSVParser parser;
	private CSVReader csvReader;

	
	public Transcoding() throws FileNotFoundException, IOException, CsvException
	{
		reader = new FileReader(new File("transcoding.csv"));
		
		parser = new CSVParserBuilder()
			    .withSeparator(';')
			    .withIgnoreQuotations(true)
			    .build();

	    csvReader = new CSVReaderBuilder(reader)
			    .withSkipLines(0)
			    .withCSVParser(parser)
			    .build();
	    
	}
	
	public String valueFromCode(String code) throws CsvValidationException, IOException
	{		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    	if (line[0].toLowerCase().equals(code.toLowerCase()))
	    		return(line[1]);
	    }
	     
	    return ""; 	    
	}
}
