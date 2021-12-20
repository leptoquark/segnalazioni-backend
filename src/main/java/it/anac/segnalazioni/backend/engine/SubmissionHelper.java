package it.anac.segnalazioni.backend.engine;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.opensagres.xdocreport.document.json.JSONObject;
import it.anac.segnalazioni.backend.domain.AntivirusServiceAdapter;
import it.anac.segnalazioni.backend.engine.model.FileDocument;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloRequest;
import it.anac.segnalazioni.backend.model.protocollo.ProtocolloResponse;
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
	
	@Autowired
	private AntivirusServiceAdapter av;
	
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
	
	private static String testJson = "{\r\n" + 
			"  \"cig\" : \"XA31927D46\",\r\n" + 
			"  \"soggettoSegnalantePanelColumns2Tipodidocumento\" : \"cartaDiIdentita\",\r\n" + 
			"  \"comune_appalti\" : \"Acireale\",\r\n" + 
			"  \"qualifica\" : \"cittadino\",\r\n" + 
			"  \"documento_fronte\" : [ {\r\n" + 
			"    \"originalName\" : \"03_RICEVUTA REVERSALE AGIBILITA'_compressed.pdf\",\r\n" + 
			"    \"size\" : 88900,\r\n" + 
			"    \"data\" : {\r\n" + 
			"      \"fieldname\" : \"file\",\r\n" + 
			"      \"path\" : \"/tmp/51e97d3ad8d580f4d70d8d41e85118f2\",\r\n" + 
			"      \"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"      \"filename\" : \"51e97d3ad8d580f4d70d8d41e85118f2\",\r\n" + 
			"      \"size\" : 88900,\r\n" + 
			"      \"form\" : \"\",\r\n" + 
			"      \"originalname\" : \"03_RICEVUTA REVERSALE AGIBILITA'_compressed.pdf\",\r\n" + 
			"      \"destination\" : \"/tmp\",\r\n" + 
			"      \"project\" : \"\",\r\n" + 
			"      \"mimetype\" : \"application/pdf\",\r\n" + 
			"      \"encoding\" : \"7bit\",\r\n" + 
			"      \"url\" : \"/%2F51e97d3ad8d580f4d70d8d41e85118f2\"\r\n" + 
			"    },\r\n" + 
			"    \"name\" : \"03_RICEVUTA REVERSALE AGIBILITA-_compressed-1d68a3cf-6f8d-4c74-9cf0-4b2a43e31d21.pdf\",\r\n" + 
			"    \"storage\" : \"url\",\r\n" + 
			"    \"type\" : \"application/pdf\",\r\n" + 
			"    \"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F51e97d3ad8d580f4d70d8d41e85118f2\"\r\n" + 
			"  } ],\r\n" + 
			"  \"page7Fieldset2EditGrid\" : [ {\r\n" + 
			"    \"note_documento\" : \"i\",\r\n" + 
			"    \"titolo_documento\" : \"i\",\r\n" + 
			"    \"documento_allegati\" : [ {\r\n" + 
			"      \"originalName\" : \"05 b_PLN_141994281_1_compressed.pdf\",\r\n" + 
			"      \"size\" : 10809,\r\n" + 
			"      \"data\" : {\r\n" + 
			"        \"fieldname\" : \"file\",\r\n" + 
			"        \"path\" : \"/tmp/3a3dd9dfffa9015712d09ef66a7ed3ac\",\r\n" + 
			"        \"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"        \"filename\" : \"3a3dd9dfffa9015712d09ef66a7ed3ac\",\r\n" + 
			"        \"size\" : 10809,\r\n" + 
			"        \"form\" : \"\",\r\n" + 
			"        \"originalname\" : \"05 b_PLN_141994281_1_compressed.pdf\",\r\n" + 
			"        \"destination\" : \"/tmp\",\r\n" + 
			"        \"project\" : \"\",\r\n" + 
			"        \"mimetype\" : \"application/pdf\",\r\n" + 
			"        \"encoding\" : \"7bit\",\r\n" + 
			"        \"url\" : \"/%2F3a3dd9dfffa9015712d09ef66a7ed3ac\"\r\n" + 
			"      },\r\n" + 
			"      \"name\" : \"05 b_PLN_141994281_1_compressed-329da0cd-23e4-4c4c-ac3d-665c58c0dfdd.pdf\",\r\n" + 
			"      \"storage\" : \"url\",\r\n" + 
			"      \"type\" : \"application/pdf\",\r\n" + 
			"      \"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F3a3dd9dfffa9015712d09ef66a7ed3ac\"\r\n" + 
			"    } ]\r\n" + 
			"  } ],\r\n" + 
			"  \"soggettoSegnalanteColumnsText2\" : \"ELISEI\",\r\n" + 
			"  \"codiceFiscale_sa\" : \"0401581087400000\",\r\n" + 
			"  \"soggettoSegnalanteColumnsText4\" : \"BNCCLD79P07H501P\",\r\n" + 
			"  \"procedura_affidamento\" : \"\",\r\n" + 
			"  \"faseprocedura\" : \"\",\r\n" + 
			"  \"page3Fieldset2DataPeriododellapresuntasegnalazione\" : \"\",\r\n" + 
			"  \"denominazione_operatoreEconomico\" : \"iii\",\r\n" + 
			"  \"ambitodellintervento\" : \"concessioneDiServiziForniture\",\r\n" + 
			"  \"provincia_appalti\" : \"Catania\",\r\n" + 
			"  \"page3Fieldset2Container\" : {\r\n" + 
			"    \"contenziosoSegnalante\" : false\r\n" + 
			"  },\r\n" + 
			"  \"codiceFiscale_operatoreEconomico\" : \"\",\r\n" + 
			"  \"area\" : \"appalti\",\r\n" + 
			"  \"dati_sensibili\" : \"\",\r\n" + 
			"  \"soggettoSegnalantePanelColumns2Documentodiriconoscimento2\" : [ ],\r\n" + 
			"  \"cig_aggiuntivi\" : [ ],\r\n" + 
			"  \"oggettoContratto_sa\" : \"iii\",\r\n" + 
			"  \"stazioneappaltante\" : \"\",\r\n" + 
			"  \"soggettiaux\" : {\r\n" + 
			"    \"ispettoratoPerLaFunzionePubblica\" : false,\r\n" + 
			"    \"procuraDellaRepubblica\" : false,\r\n" + 
			"    \"rpct\" : false,\r\n" + 
			"    \"procuraRegionaleCorteDeiConti\" : false,\r\n" + 
			"    \"prefettura\" : false,\r\n" + 
			"    \"altro\" : false\r\n" + 
			"  },\r\n" + 
			"  \"regione_appalti\" : \"Sicilia\",\r\n" + 
			"  \"soggettoSegnalanteColumnsText\" : \"Paride\",\r\n" + 
			"  \"denominazione_sa\" : \"SO.G.I.P. SRL UNIPERSONALE\",\r\n" + 
			"  \"codice_documento\" : \"AL 304 AH\",\r\n" + 
			"  \"descrizione_intervento_segnalazione\" : \"FORNITURA DI RAM AGGIUNTIVA PER SERVER CENTRALE\"\r\n" + 
			"}";
	
	
	public static void main(String[] argv) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(testJson.toString());
			
		String docFronte_name = jsonNode.findValues("documento_fronte").get(0).get(0).get("name").asText();
		String docFronte_url = jsonNode.findValues("documento_fronte").get(0).get(0).get("url").asText();
		
		String docRetro_name = "";
		String docRetro_url = "";
		if (! jsonNode.findValues("documento_retro").isEmpty())
		{
			docRetro_name = jsonNode.findValues("documento_retro").get(0).get(0).get("name").asText();
			docRetro_url = jsonNode.findValues("documento_retro").get(0).get(0).get("url").asText();
		}
		
	}
		
	private String invioProtocollo(String submissionId, boolean zip) throws IOException {
		
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
		if (!jsonNode.findValues("documento_retro").isEmpty())
		{
			docRetro_name = jsonNode.findValues("documento_retro").get(0).get(0).get("name").asText();
			docRetro_url = jsonNode.findValues("documento_retro").get(0).get(0).get("url").asText();
		}
		
		
		/*String nome    = nameNode.get("nomeSegnalante").asText();
		String cognome = nameNode.get("cognomeSegnalante").asText();*/
		String nome = "CLAUDIO";
		String cognome = "BIANCALANA";
		
		if (!av.checkVirusOnUrl(docFronte_url) && !av.checkVirusOnUrl(docRetro_url))
			return "VIRUS-KO";
		
		LinkedList<FileDocument> docs = new LinkedList<FileDocument>();
		docs.add(new FileDocument(docFronte_url, docFronte_name));
		docs.add(new FileDocument(docRetro_name, docRetro_url));

		ProtocolloRequest pr = new ProtocolloRequest();
		
		pr.setIdentificazioneAoo("ANAC");
		pr.setIdentificazioneUfficio("ANAC");
		pr.setProtocolloTipoProtocollo("I");
	
		pr.setProtocolloOggetto("segnalazione-web da "+nome+" "+cognome);
		pr.setProtocolloMittente(nome+" "+cognome);
		
		pr.setProtcolloTipoDocumento("lettera");
		
		// TODO: Settare l'ufficio con l'agoritmo di Giancarlo
		pr.setAssegnatarioUfficio("ufficio1");
		
		pr.setAssegnatarioCompetenza(1);
		// valido per tutti i documenti
		pr.setDocumentoTipoDocumento("P");
		
		String zippedFile = System.getProperty("java.io.tmpdir") +File.separator+System.currentTimeMillis()+"_"+cognome+".zip";
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
		

		return ret.getNumeroProtocollo();
	}
}
