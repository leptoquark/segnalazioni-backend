package it.anac.segnalazioni.backend.engine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.opensagres.xdocreport.document.json.JSONObject;
import it.anac.segnalazioni.backend.domain.AntivirusServiceAdapter;
import it.anac.segnalazioni.backend.engine.model.FileDocument;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloResponse;
import it.anac.segnalazioni.backend.rest.ProtocolloService;

@RestController
@RequestMapping(path="/ws")
public class SubmissionHelper
{
    private Logger logger = LoggerFactory.getLogger(SubmissionHelper.class);

	@Autowired
	private ProtocolloService protocolloService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private AntivirusServiceAdapter av;
	
	@Autowired
	private MailSenderHelper msh;
		
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/protocollo")
	public ProtocolloResponse invioProtocollo(@RequestParam String submissionId) throws IOException, MessagingException
	{
		return invioProtocollo(submissionId, false);
	}
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/protocollo_zip")
	public ProtocolloResponse invioProtocolloZip(@RequestParam String submissionId) throws IOException, MessagingException
	{
		return invioProtocollo(submissionId, true);
	}
			
	private ProtocolloResponse invioProtocollo(String submissionId, boolean zip) throws IOException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(submissionId));
		JSONObject res = mongoTemplate.findOne(query,JSONObject.class, "submissions");

		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode jsonNode = objectMapper.readTree(res.toString());
			
		JsonNode nameNode = jsonNode.at("/data");	
		
		String docFronte_name = nameNode.findValues("documento_fronte").get(0).get(0).get("name").asText();
		String docFronte_url = nameNode.findValues("documento_fronte").get(0).get(0).get("url").asText();
		
		String docRetro_name = "";
		String docRetro_url = "";
		
		if (nameNode.findValues("documento_retro").get(0).get(0)!=null)
		{
			docRetro_name = nameNode.findValues("documento_retro").get(0).get(0).get("name").asText();
			docRetro_url = nameNode.findValues("documento_retro").get(0).get(0).get("url").asText();
		}
		
		String nome_segnalante    = nameNode.get("nome_soggetto_segnalante").asText();
		String cognome_segnalante = nameNode.get("cognome_soggetto_segnalante").asText();
		String email_segnalante = nameNode.get("email_soggetto_segnalante").asText();
	
		logger.debug("Controllo antivirus");
		if (!av.checkVirusOnUrl(docFronte_url) && !av.checkVirusOnUrl(docRetro_url))
		{
			ProtocolloResponse pr = new ProtocolloResponse();
			pr.setData("");
			pr.setEsito("VIRUS-KO");
			pr.setMessaggio("Nei file è cotenuto un virus");
			pr.setNumeroProtocollo("");
			
			logger.debug("File con Virus!");
		
			return pr;
		}
		
		LinkedList<FileDocument> docs = new LinkedList<FileDocument>();
		docs.add(new FileDocument(docFronte_url, docFronte_name));
		docs.add(new FileDocument(docFronte_url, docFronte_name));
		docs.add(new FileDocument(docFronte_url, docFronte_name));
		docs.add(new FileDocument(docFronte_url, docFronte_name));
		
		docs.add(new FileDocument(docRetro_url, docRetro_name));

		ProtocolloRequest pr = new ProtocolloRequest();
		
		pr.setIdentificazioneAoo("ANAC");
		pr.setIdentificazioneUfficio("ANAC");
		pr.setProtocolloTipoProtocollo("I");
	
		pr.setProtocolloOggetto("segnalazione-web da "+nome_segnalante+" "+cognome_segnalante);
		pr.setProtocolloMittente(nome_segnalante+" "+cognome_segnalante);
		
		pr.setProtcolloTipoDocumento("lettera");
		
		// TODO: Settare l'ufficio con l'agoritmo di Giancarlo
		pr.setAssegnatarioUfficio("ufficio1");
		
		pr.setAssegnatarioCompetenza(1);
		// valido per tutti i documenti
		pr.setDocumentoTipoDocumento("P");
		
		String zippedFile = System.getProperty("java.io.tmpdir") +File.separator+System.currentTimeMillis()+"_"+cognome_segnalante+".zip";
		if (zip)
		{
			FileHelper fh = new FileHelper();
			fh.zipMultipleUrls(docs, zippedFile);
			FileDocument[] fd = new FileDocument[1];
			FileDocument zippedFileDocument = new FileDocument(zippedFile);
			fd[0]=zippedFileDocument;
			pr.setFileDocuments(fd);
		}
		else
		{
			FileDocument[] array = Arrays.copyOf(docs.toArray(),
					 docs.toArray().length,
					 FileDocument[].class);
			pr.setFileDocuments(array);
		}
		
		//TODO da aggiungere il report appena disponibile, prelevandolo dall'ID.
		ProtocolloResponse ret = protocolloService.invio(
							pr.getIdentificazioneAoo(),
							pr.getIdentificazioneUfficio(),
							pr.getProtocolloTipoProtocollo(),
							pr.getProtocolloOggetto(),
							pr.getProtocolloMittente(),
							pr.getProtcolloTipoDocumento(),
							pr.getAssegnatarioUfficio(),
							pr.getAssegnatarioCompetenza(),
							pr.getDocumentoTipoDocumento(),
							pr.getFileDocuments());
		
		// Invio della mail con allegato il pdf della segnalazione
		try {	
			msh.sendMessage(email_segnalante,
					"Segnalazioni ANAC prot. "+ret.getNumeroProtocollo(),
					"In allegato la segnalazione ANAC",
					"template_appalti.odt",
					"template_appalti.odt");
		} catch (MessagingException e) {
			logger.error("Invio fallito per "+email_segnalante,e);
		}

		return ret;
	}
}
