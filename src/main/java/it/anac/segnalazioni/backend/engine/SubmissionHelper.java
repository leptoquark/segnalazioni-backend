package it.anac.segnalazioni.backend.engine;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.opensagres.xdocreport.document.json.JSONObject;
import it.anac.segnalazioni.backend.engine.model.FileDocument;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;
import it.anac.segnalazioni.backend.rest.AntivirusServiceAdapterRestImpl;
import it.anac.segnalazioni.backend.rest.ProtocolloService;

@RestController
@RequestMapping(path="/ws")
public class SubmissionHelper
{
	@Autowired
	private ProtocolloService protocolloService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@GetMapping("/protocollo")
	public String invioProtocollo(@RequestParam String submissionId) throws IOException
	{
		return invioProtocollo(submissionId, false);
	}
	  
	@GetMapping("/protocollo_zip")
	public String invioProtocolloZip(@RequestParam String submissionId) throws IOException
	{
		return invioProtocollo(submissionId, true);
	}
	
	private AntivirusServiceAdapterRestImpl av = new AntivirusServiceAdapterRestImpl();
		
	private String invioProtocollo(String submissionId, boolean zip) throws IOException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(submissionId));
		JSONObject res = mongoTemplate.findOne(query,JSONObject.class, "submissions");

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(res.toString());
		
		JsonNode nameNode = jsonNode.at("/data");	
		
		System.out.println(nameNode.toPrettyString());
		
		// Inserire tutti i possibili documenti
		/*String docFronte_name = nameNode.findValues("documento_fronte").get(0).get("name").asText();
		String docFronte_url  = nameNode.findValues("documento_fronte").get(0).get("url").asText();*/
		
		String docFronte_name = "documento_fronte.pdf";
		String docFronte_url = "http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F51e97d3ad8d580f4d70d8d41e85118f2";
		
		/*String docRetro_name = nameNode.findValues("documento_retro").get(0).get("name").asText();
		String docRetro_url  = nameNode.findValues("documento_retro").get(0).get("url").asText();*/
		
		/*String nome    = nameNode.get("nomeSegnalante").asText();
		String cognome = nameNode.get("cognomeSegnalante").asText();*/
		String nome = "CLAUDIO";
		String cognome = "BIANCALANA";
		
		/*if (av.checkVirusOnUrl(docFronte_url))// || av.checkVirusOnUrl(docRetro_url))
			return "VIRUS-KO";*/
		
		LinkedList<FileDocument> docs = new LinkedList<FileDocument>();
		docs.add(new FileDocument(docFronte_name, docFronte_url));
		//docs.add(new FileDocument(docRetro_name, docRetro_url));

		ProtocolloRequest pr = new ProtocolloRequest();
		
		pr.setIdentificazioneAoo("ANAC");
		pr.setIdentificazioneUfficio("ANAC");
		pr.setProtocolloTipoProtocollo("I");
	
		pr.setProtocolloOggetto("segnalazione-web da "+nome+" "+cognome);
		pr.setProtocolloMittente(nome+" "+cognome);
		
		pr.setProtcolloTipoDocumento("modulo-web");
		
		// TODO: Settare l'ufficio con l'agoritmo di Giancarlo
		pr.setAssegnatarioUfficio("ufficio1");
		
		pr.setAssegnatarioCompetenza(1);
		// valido per tutti i documenti
		pr.setDocumentoTipoDocumento("P");
		
		String zippedFile = System.currentTimeMillis()+"_"+cognome+".zip";
		if (zip)
		{
			FileHelper fh = new FileHelper();
			fh.zipMultipleUrls(docs, zippedFile);
			FileDocument[] fd = new FileDocument[1];
			fd[0].setFilename(zippedFile);
			pr.setFileDocuments(fd);
		}
		else
			pr.setFileDocuments((FileDocument[])docs.toArray());
		
		//TODO da aggiungere il report appena disponibile, prelevandolo dall'ID.
		return protocolloService.invio(
							pr.getIdentificazioneAoo(),
							pr.getIdentificazioneUfficio(),
							pr.getProtocolloTipoProtocollo(),
							pr.getProtocolloOggetto(),
							pr.getProtocolloMittente(),
							pr.getProtcolloTipoDocumento(),
							pr.getAssegnatarioUfficio(),
							pr.getAssegnatarioCompetenza(),
							pr.getDocumentoTipoDocumento(),
							pr.getFileDocuments()).getNumeroProtocollo();
	}
}
