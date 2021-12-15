package it.anac.segnalazioni.backend.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class IstatCodes {
		
	private FileReader reader;
	private CSVParser parser;
	private CSVReader csvReader;

	
	public IstatCodes() throws FileNotFoundException, IOException, CsvException
	{
		reader = new FileReader(new File("istat.csv"));
		
		parser = new CSVParserBuilder()
			    .withSeparator(';')
			    .withIgnoreQuotations(true)
			    .build();

	    csvReader = new CSVReaderBuilder(reader)
			    .withSkipLines(0)
			    .withCSVParser(parser)
			    .build();
	    
	}
	
	public List getProvinceFromRegione(String regione) throws CsvValidationException, IOException
	{
		HashSet<String> province = new HashSet<String>();
		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    	if (line[1].toLowerCase().equals(regione.toLowerCase()))
	    		province.add(line[4]);
	    }
	    
	    Object[] sorted_province = province.toArray();
	    Arrays.sort(sorted_province);    
	    return Arrays.asList(sorted_province); 	    
	}
	
	public List getComuniFromProvincia(String provincia) throws CsvValidationException, IOException
	{
		HashSet<String> comuni = new HashSet<String>();
		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    	if (line[4].toLowerCase().equals(provincia.toLowerCase()))
	    		comuni.add(line[3]);
	    }
	    
	    Object[] sorted_comuni = comuni.toArray();
	    Arrays.sort(sorted_comuni);    
	    return Arrays.asList(sorted_comuni); 
	    
	}
	
	public List getRegioni() throws CsvValidationException, IOException
	{
		HashSet<String> regioni = new HashSet<String>();
		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    		regioni.add(line[1]);
	    }
	    
	    Object[] sorted_regioni = regioni.toArray();
	    Arrays.sort(sorted_regioni);    
	    return Arrays.asList(sorted_regioni);
	}
	
	public String getRegioneFromProvincia(String provincia) throws CsvValidationException, IOException
	{
		String notFound = "";
		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    		if (line[3].toLowerCase().trim().equals(
	    						provincia.toLowerCase().trim()))
	    			return line[1];
	    }
	    return notFound;
	}
	
	public String getProvinciaFromComune(String comune) throws CsvValidationException, IOException
	{
		String notFound = "";
		
	    String[] line;
	    while ((line = csvReader.readNext()) != null) {
	    		if (line[3].toLowerCase().trim().equals(
	    						comune.toLowerCase().trim()))
	    			return line[4];
	    }
	    return notFound;
	}
	
	public static void main(String[] argv) throws FileNotFoundException, IOException, CsvException
	{
		IstatCodes ic = new IstatCodes();
		
		/*List list = ic.getComuniFromProvincia("Roma");
		Iterator iter = list.iterator();
		while(iter.hasNext())
			System.out.println(iter.next().toString());
		
		System.out.println(ic.getRegioneFromProvincia("ROMA"));*/
		System.out.println(ic.getProvinciaFromComune("Anguillara Sabazia"));
	}

}
