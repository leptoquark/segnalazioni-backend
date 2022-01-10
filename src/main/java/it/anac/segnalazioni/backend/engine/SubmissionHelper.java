package it.anac.segnalazioni.backend.engine;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.json.JSONObject;
import it.anac.segnalazioni.backend.domain.AntivirusServiceAdapter;
import it.anac.segnalazioni.backend.engine.model.FileDocument;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloResponse;
import it.anac.segnalazioni.backend.report.util.ReportHelperPdf;
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
	
	@Value("${protocollo.tipodocumento}")
    private String tipoDocumento;

		
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
	@GetMapping("/protocollo")
	public ProtocolloResponse invioProtocollo(@RequestParam String submissionId) throws IOException, MessagingException, ParseException, XDocReportException
	{
		return invioProtocolloWorker(submissionId);
	}
	
	private String getValueFromJson(JsonNode nameNode, String prop)
	{
		String ret = "";
		if (nameNode!=null)
			if (nameNode.get(prop)!=null)
				ret = nameNode.get(prop).asText();
		return ret;
	}
	
	private boolean checkVirus(LinkedList<FileDocument> docList)
	{
		boolean res = false;
		
		for(int i=0; i<docList.size(); i++)
		{
			if (docList.get(i).getUrl()!=null)
				res = av.checkVirusOnUrl(docList.get(i).getUrl().toString()); 
			
			if (res)
				return res;
		}
		
		return res;
	}
	
	private ProtocolloResponse invioProtocolloWorker(String submissionId) throws IOException, ParseException, XDocReportException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(submissionId));
		JSONObject res = mongoTemplate.findOne(query,JSONObject.class, "submissions");

		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode jsonNode = objectMapper.readTree(res.toString());
			
		JsonNode nameNode = jsonNode.at("/data");	
		
		LinkedList<FileDocument> docs = new LinkedList<FileDocument>();
		
		String docFronte_name = nameNode.findValues("documento_fronte").get(0).get(0).get("originalName").asText();
		String docFronte_url = nameNode.findValues("documento_fronte").get(0).get(0).get("url").asText();
		docs.add(new FileDocument(docFronte_url, docFronte_name,false));
		
		String docRetro_name = "";
		String docRetro_url = "";
		if (nameNode.findValues("documento_retro").get(0).get(0)!=null)
		{
			docRetro_name = nameNode.findValues("documento_retro").get(0).get(0).get("originalName").asText();
			docRetro_url = nameNode.findValues("documento_retro").get(0).get(0).get("url").asText();
		}
		docs.add(new FileDocument(docRetro_url, docRetro_name,false));
		
		
		if (nameNode.get("documenti_attivita_rpct_trasparenza")!=null &&
				!nameNode.get("documenti_attivita_rpct_trasparenza").isEmpty())
		{
			JsonNode arrNode = nameNode.get("documenti_attivita_rpct_trasparenza");
			if (arrNode.isArray()) {
			    for (JsonNode objNode : arrNode)
			    {
			    	System.out.println("RPCT: "+objNode.toPrettyString());
					String docTrasparenzaOIV_name = getValueFromJson(objNode,"originalName");
					String docTrasparenzaOIV_url = getValueFromJson(objNode,"url");
					docs.add(new FileDocument(docTrasparenzaOIV_name, docTrasparenzaOIV_url,false));
				}
			}
		}
		
		if (nameNode.get("documenti_oiv_trasparenza")!=null &&
				!nameNode.get("documenti_oiv_trasparenza").isEmpty())
		{
			JsonNode arrNode = nameNode.get("documenti_oiv_trasparenza");
			if (arrNode.isArray()) {
			    for (JsonNode objNode : arrNode)
				{
			    	System.out.println("OIV: "+objNode.toPrettyString());
					String docTrasparenzaOIV_name = getValueFromJson(objNode,"originalName");
					String docTrasparenzaOIV_url = getValueFromJson(objNode,"url");
					docs.add(new FileDocument(docTrasparenzaOIV_name, docTrasparenzaOIV_url,false));
				}
			}
		}
	
		JsonNode arrNode_cig = nameNode.get("documenti_allegati_chiusura");
		if (arrNode_cig.isArray()) {
		    for (JsonNode objNode : arrNode_cig) {
		        String docChiusura_name  = getValueFromJson(objNode.get("documento_allegati").get(0),"originalName");
		        String docChiusura_url   = getValueFromJson(objNode.get("documento_allegati").get(0),"url");
		        docs.add(new FileDocument(docChiusura_url, docChiusura_name,false));
		    }
		}	
		
		String nome_segnalante    = nameNode.get("nome_soggetto_segnalante").asText();
		String cognome_segnalante = nameNode.get("cognome_soggetto_segnalante").asText();
		String email_segnalante = nameNode.get("email_soggetto_segnalante").asText();
	
		logger.debug("Controllo antivirus");
		
		if (!checkVirus(docs))
		{
			ProtocolloResponse pr = new ProtocolloResponse();
			pr.setData("");
			pr.setEsito("VIRUS-KO");
			pr.setMessaggio("Nei file è cotenuto un virus");
			pr.setNumeroProtocollo("");
			
			logger.debug("File con Virus!");
			
			msh.sendMessageBackground(email_segnalante,
					"Segnalazione ANAC: errore nella sottomissione",
					"Gentile utente,\n"+
					"E' stato rilevato un virus nei file allegati al modulo di segnalazione,\n"+
					"pertanto non è stato possibile procedere al completamento della sottomissione. \n"+
					"Si prega di verificare il contenuto degli allegati e tentare nuovamente l'invio."+
					"\n\nCordiali Saluti,\n Lo staff tecnico di ANAC","","");
		
			return pr;
		}
		

		ProtocolloRequest pr = new ProtocolloRequest();
		
		pr.setIdentificazioneAoo("ANAC");
		pr.setIdentificazioneUfficio("ANAC");
		pr.setProtocolloTipoProtocollo("I");
	
		pr.setProtocolloOggetto("segnalazione-web da "+nome_segnalante+" "+cognome_segnalante);
		pr.setProtocolloMittente(nome_segnalante+" "+cognome_segnalante);
	
		pr.setProtcolloTipoDocumento(tipoDocumento);
		
		RoutingRuleEngine router = new RoutingRuleEngine(res.toString());
		pr.setAssegnatarioUfficio(router.getRoute());
		
		pr.setAssegnatarioCompetenza(1);

		ReportHelperPdf rhp = new ReportHelperPdf();
		String filePath = rhp.getPdfReport(res.toString(),submissionId+"_"+System.currentTimeMillis()+".pdf");
		docs.add(new FileDocument(filePath,true));
		
		FileDocument[] array = Arrays.copyOf(docs.toArray(),
				 docs.toArray().length,
				 FileDocument[].class);
		pr.setFileDocuments(array);
		
		ProtocolloResponse ret = protocolloService.invio(
							pr.getIdentificazioneAoo(),
							pr.getIdentificazioneUfficio(),
							pr.getProtocolloTipoProtocollo(),
							pr.getProtocolloOggetto(),
							pr.getProtocolloMittente(),
							pr.getProtcolloTipoDocumento(),
							pr.getAssegnatarioUfficio(),
							pr.getAssegnatarioCompetenza(),
							pr.getFileDocuments());
		
		if (ret.getEsito().equals("0000"))
				msh.sendMessageBackground(email_segnalante,
						"Segnalazione ANAC prot. "+ret.getNumeroProtocollo(),
						"Gentile utente,\n"+
						"in allegato la segnalazione ANAC sottomessa in data "+ret.getData()+"\n"+
						"Il protocollo assegnato alla segnalazione è "+ret.getNumeroProtocollo()+
						"\n\nCordiali Saluti,\n Lo staff tecnico di ANAC",
						"sottomissione_prot_"+ret.getNumeroProtocollo()+".pdf",
						filePath);
		else
			msh.sendMessageBackground(email_segnalante,
					"Segnalazione ANAC: errore nella sottomissione",
					"Gentile utente,\n"+
					"La segnalazione non è stata sottomessa per un errore interno, si prega di tentare nuovamente."+
					"\n\nCordiali Saluti,\n Lo staff tecnico di ANAC","","");
			
		return ret;
	}
}
