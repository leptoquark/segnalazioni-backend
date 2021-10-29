package it.anac.segnalazioni.backend.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.anac.segnalazioni.backend.domain.AntivirusServiceAdapter;
import it.anac.segnalazioni.backend.domain.antivirus.ClamScan;
import it.anac.segnalazioni.backend.domain.antivirus.ScanResult;

@Service
public class AntivirusServiceAdapterRestImpl implements AntivirusServiceAdapter
{
	@Value("${antivirus.clamav.clamAvUrl}")
    private String clamAvUrl;
	
	@Value("${antivirus.clamav.clamAvPort}")
    private int clamAvPort;
	
	@Value("${antivirus.clamav.clamAVTimeout}")
    private int clamAVTimeout;
	
	
	public boolean checkVirusOnUrl(String url)
	{
		boolean res = false;
		URL toDownload;
		try {
			toDownload = new URL(url);
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        byte[] chunk = new byte[4096];
	        int bytesRead;
	        InputStream stream = toDownload.openStream();
	        while ((bytesRead = stream.read(chunk)) > 0) 
	            outputStream.write(chunk, 0, bytesRead);
	        
	        res = testAntivirus(outputStream.toByteArray()); 
		} catch (IOException e) {
			e.printStackTrace();
			res = false;
		}     
	     return res;
	}
	
	private boolean testAntivirus(byte[] allegatoBytes) throws IOException
	{
		ClamScan scanner;
		ScanResult result= null;
		
		boolean res = true;
		
		scanner = new ClamScan();
        scanner.setHost(clamAvUrl);
        scanner.setPort(clamAvPort);
        scanner.setTimeout(clamAVTimeout);
		
        result = scanner.scan(allegatoBytes);
        
        ScanResult.Status risultatoStato = result.getStatus();	
        
        if(risultatoStato.equals(ScanResult.Status.FAILED) || risultatoStato.equals(ScanResult.Status.ERROR))
        	res = false;
               
        return res;
	}
}
