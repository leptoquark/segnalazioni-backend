package it.anac.segnalazioni.backend.rest;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.anac.segnalazioni.backend.engine.FileHelper;
import it.anac.segnalazioni.backend.engine.model.FileDocument;
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
	
	private byte[] downloadUrl(URL toDownload) {
		FileHelper fh = new FileHelper();
		return fh.downloadUrl(toDownload);
	}
	
	private byte[] downloadFile(String file) throws IOException {
		FileHelper fh = new FileHelper();
		return fh.file2byte(file);
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
	 * @throws IOException 
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
									FileDocument[] fileDocuments) throws IOException
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
    	
    	
    	
    	
    	for(int i=0; i<fileDocuments.length; i++)
    	{
        	ProtocolloTypeDocumenti documenti = new ProtocolloTypeDocumenti();   	
	    	DocumentoType documento = new DocumentoType();
	    	documento.setTipoDocumento(documentoTipoDocumento);
	    	documento.setNomeFile(fileDocuments[i].getFilename());
	    	URL url = fileDocuments[i].getUrl();
	    	
	    	if (url!=null)
	    		documento.setFileBase64(downloadUrl(url));
	    	else if (!documento.getNomeFile().equals(""))
	    		documento.setFileBase64(downloadFile(documento.getNomeFile()));

	    	if (!documento.getNomeFile().equals(""))
	    	{
	    		documenti.setDocumento(documento);
	    		protocollo.getDocumenti().add(documenti);
	    	}
    	}
    	
    	
	
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
