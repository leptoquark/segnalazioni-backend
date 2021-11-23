package it.anac.segnalazioni.backend.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.anac.segnalazioni.backend.model.protocollo.ProtocolloResponse;
import it.anac.segnalazioni.client.protocollo.AssegnatarioType;
import it.anac.segnalazioni.client.protocollo.DocumentoType;
import it.anac.segnalazioni.client.protocollo.IdentificazioneType;
import it.anac.segnalazioni.client.protocollo.ProtocolloType;
import it.anac.segnalazioni.client.protocollo.ProtocolloTypeAssegnatari;
import it.anac.segnalazioni.client.protocollo.ProtocolloTypeDocumenti;
import it.anac.segnalazioni.client.protocollo.ProtocolloWS;
import it.anac.segnalazioni.client.protocollo.ProtocolloWS_Service;
import it.anac.segnalazioni.client.protocollo.RegistraRequestType;
import it.anac.segnalazioni.client.protocollo.ResponseType;

@Service
public class ProtocolloService {
	
	@Value("${protocollo.ws.utente}")
    private String utente;
	
	@Value("${protocollo.ws.password}")
    private String password;
	
	public byte[] downloadUrl(URL toDownload) {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try {
	        byte[] chunk = new byte[4096];
	        int bytesRead;
	        InputStream stream = toDownload.openStream();

	        while ((bytesRead = stream.read(chunk)) > 0) {
	            outputStream.write(chunk, 0, bytesRead);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return outputStream.toByteArray();
	}
	
	/**
	 * 
	 * Discutere con Pozzaglia e Cerniera
	 * 
	 * @param identificazioneAoo : ANAC
	 * @param identificazioneUfficio : ANAC
	 * @param protocolloTipoProtocollo : O
	 * @param protocolloOggetto: TEST
	 * @param protocolloMittente: test test
	 * @param protocolloTipoDocumento: lettera
	 * @param assegnatarioUfficio: ufficio1
	 * @param assegnatarioCompetenza: 1
	 * @param documentoTipoDocumento: P
	 * @param documentoNomeFile: test.dpf
	 * @param documentoUrlDocumento: https://www.anticorruzione.it/documents/91439/129009/Avviso+pubblicazione+esito+prova+orale+e+approvazione+graduatoria+finale+F6IT.pdf/cd5c33c9-40d8-2afb-1f1d-01aacd3df9df?t=1589372298123
	 * @return
	 * @throws MalformedURLException
	 */
		
	public ProtocolloResponse invio(String identificazioneAoo,
									String identificazioneUfficio,
									String protocolloTipoProtocollo,
									String protocolloOggetto,
									String protocolloMittente,
									String protocolloTipoDocumento,
									String assegnatarioUfficio,
									int    assegnatarioCompetenza,
									String documentoTipoDocumento,
									String documentoNomeFile,
									String documentoUrlDocumento) throws MalformedURLException
	{
		
		ProtocolloWS_Service service = new ProtocolloWS_Service();
    	ProtocolloWS client = service.getProtocolloWSSOAP();
    	
    	RegistraRequestType registraProtocolloRequest = new RegistraRequestType();    
    	
    	IdentificazioneType identificazione = new IdentificazioneType();
    	identificazione.setAoo(identificazioneAoo);
    	identificazione.setUfficio(identificazioneUfficio);
    	identificazione.setUtente(utente);
    	identificazione.setPassword(password);
    	
    	ProtocolloType protocollo = new ProtocolloType();
    	protocollo.setTipoProtocollo(protocolloTipoProtocollo);
    	protocollo.setOggetto(protocolloOggetto);
    	protocollo.setMittente(protocolloMittente);
    	protocollo.setTipoDocumento(protocolloTipoDocumento);
    	
    	AssegnatarioType assegnatario = new AssegnatarioType();
    	assegnatario.setUfficio(assegnatarioUfficio);
    	assegnatario.setCompetenza(assegnatarioCompetenza);
    	
    	ProtocolloTypeAssegnatari assegnatari = new ProtocolloTypeAssegnatari();
    	assegnatari.setAssegnatario(assegnatario);
    	protocollo.getAssegnatari().add(assegnatari);
    	
    	DocumentoType documento = new DocumentoType();
    	documento.setTipoDocumento(documentoTipoDocumento);
    	documento.setNomeFile(documentoNomeFile);
    	URL url = new URL(documentoUrlDocumento);		    	
    	byte[] encoded = downloadUrl(url);
    	documento.setFileBase64(encoded);
       
    	ProtocolloTypeDocumenti documenti = new ProtocolloTypeDocumenti();
    	documenti.setDocumento(documento);
    	protocollo.getDocumenti().add(documenti);
	
    	registraProtocolloRequest.setIdentificazione(identificazione);
    	registraProtocolloRequest.setProtocollo(protocollo);
    	    	   
    	ResponseType aux = client.registraProtocollo(registraProtocolloRequest);

    	ProtocolloResponse ret = new ProtocolloResponse();
    	ret.setEsito(aux.getEsito());
    	ret.setMessaggio(aux.getDescrizioneEsito());
    	ret.setNumeroProtocollo(aux.getNumeroProtocollo());
    	ret.setData(aux.getDataProtocollo());
    	
    	return ret;
	}
}
