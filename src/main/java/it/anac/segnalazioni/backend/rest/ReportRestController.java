package it.anac.segnalazioni.backend.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.json.JSONObject;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.freemarker.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import it.anac.segnalazioni.backend.report.model.Allegato;
import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Rup;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneAppalto;
import it.anac.segnalazioni.backend.report.util.MyTemplateExceptionHandler;

@RestController
@RequestMapping(path="/ws")
public class ReportRestController
{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/report", method = RequestMethod.GET)	
	public ResponseEntity<InputStreamResource> download(
			@RequestParam(defaultValue = "") String id) throws IOException, XDocReportException, ParseException {

		boolean appalto = true; // messo per test a true
		boolean corruzione = false;
		boolean incarichi = false;
		boolean rpct = false;
		boolean trasparenza = false;
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		JSONObject res = mongoTemplate.findOne(query,JSONObject.class, "submissions");
		
		
		/*********************************************/
	
		ObjectMapper objectMapper = new ObjectMapper();
			
		JsonNode jsonNode = objectMapper.readTree(res.toString());
		JsonNode nameNode = jsonNode.at("/data");
				
		/****************************************/
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		
		Segnalante segnalante = new Segnalante(nameNode.get("nome_soggetto_segnalante").asText(),
											   nameNode.get("cognnome_soggetto_segnalante").asText(),
											   nameNode.get("codiceFiscale_soggetto_segnalante").asText(),
											   nameNode.get("qualifica").asText());

		Organizzazione org = new Organizzazione(nameNode.get("denominazione").asText(),
												nameNode.get("regione").asText(),
												nameNode.get("provincia").asText(),
												nameNode.get("comune").asText());
		org.setTipoEnte("Comune");
		org.setMail("prova@bugliano.gov.it");
		org.setPec("protocollo@bugliano.pec.it");
		org.setTelefono("+39 06060606");
		segnalante.setEnte(org);

		Organizzazione sa = new Organizzazione("Comune di Bugliano", "Toscana", "Pisa", "Bugliano");
		sa.setTipoEnte("Soggetto aggregatore");

		SegnalazioneAppalto segnalazione = new SegnalazioneAppalto(segnalante, "Oggetto segnalazione", 
				dateformat.parse("07-12-2021"), sa, "Fornitura di banchi a rotelle", "Appalto di Servizi/Forniture");

		// Operatore Economico
		Organizzazione oe = new Organizzazione("Lavori SPA", "Toscana", "Firenze", "Fucecchio");
		segnalazione.setOe(oe);
		
		segnalazione.setCig("0079814896");
		
		// Aggiungiamo ulteriori CIG
		segnalazione.addCig("01022012EE");
		segnalazione.addCig("008512575D");
		
		// Secretato
		segnalazione.setSecretato(true);
		segnalazione.setGenerale(true);
		segnalazione.setPerSe(true);
		
		// Importo base d'asta
		segnalazione.setBaseAsta(400000);
		
		// Procedura di affidamento
		segnalazione.setProcedura("Procedura aperta");
		
		// Fase
		segnalazione.setFase("Esecuzione del contratto");
		segnalazione.setDataFase(dateformat.parse("20-02-2021"));
		
		segnalazione.setImporto(350000);
		
		// RUP
		segnalazione.setRup(new Rup("Brunella", "Talarico"));
		
		// Data della presunta violazione
		segnalazione.setDataViolazione("ottobre 2020");
		
		// Aggiungi tutti i possibili contenziosi
		segnalazione.setContenzioso(true);
		
		for (ContenziosoType ctype : ContenziosoType.values()) {
			Contenzioso cont = new Contenzioso(ctype);
			cont.setEstremi("ac viverra felis nunc ut ipsum. Nunc condimentum lacus");
			segnalazione.addContenzioso(cont);
		}
		
		// Aggiungi altre segnalazioni
		segnalazione.setAltreSegnalazioni(true);
		segnalazione.setEstremiSegnalazioni("lorem vel metus commodo condimentum congue eget urna.");
		
		// Parametri di chiusura

		// Aggiungiamo alcuni allegati
		segnalazione.addAllegato(new Allegato("contratto.pdf", "Contratto stipulato", "Documento contrattuale"));
		segnalazione.addAllegato(new Allegato("lettera.pdf", "Lettera incarico"));
		segnalazione.addAllegato(new Allegato("documento.pdf", "Lettera incarico"));
		
		// Aggiungiamo altri soggetti interessati
		// segnalazione.addAltroSoggetto(new String("Nessuno"));
		segnalazione.addAltroSoggetto(new String("Procura della Repubblica"));
		segnalazione.addAltroSoggetto(new String("Prefettura"));

		// Aggiungiamo Dati sensibili di cui il richiedente chiede esclusione da
		// pubblicazione
		segnalazione
				.setEsclusione("Nunc at urna sit amet nunc rutrum cursus ut ac dolor. Nulla eleifend felis viverra, "
						+ "dapibus lectus sed, blandit velit. Nulla fringilla mi eu enim malesuada interdum. Duis non convallis mauris. "
						+ "Aenean volutpat lorem vel metus commodo condimentum congue eget urna. Maecenas vehicula, ligula eget sodales "
						+ "hendrerit, sapien sapien egestas diam, ac viverra felis nunc ut ipsum. Nunc condimentum lacus urna, vitae "
						+ "commodo mi gravida eget.");
	
		

		
	    String filePath = System.getProperty("java.io.tmpdir")+"/out_"+id+"_"+System.currentTimeMillis()+".pdf";
	    
	    String template_file = "";
	    if (appalto)
	    	template_file = "template_appalti.odt";
	    else if (corruzione)
	    	template_file = "template_corruzioe.odt";
	    else if (incarichi)
	    	template_file = "template_incarichi.odt";
	    else if (rpct)
	    	template_file = "template_rpct.odt";
	    else if (trasparenza)
	    	template_file = "template_trasparenza.odt";
	    
	    File initialFile = new File(template_file);
	    InputStream in = new FileInputStream(initialFile);
	    
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		cfg.setLocale(java.util.Locale.ITALIAN);
		cfg.setDateFormat("d MMMMM yyyy");
		
		cfg.setTemplateExceptionHandler(new MyTemplateExceptionHandler());

		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
				TemplateEngineKind.Freemarker);

		FreemarkerTemplateEngine templateEngine = (FreemarkerTemplateEngine) report.getTemplateEngine();
		templateEngine.setFreemarkerConfiguration(cfg);
		report.setTemplateEngine(templateEngine);

		Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

		IContext ctx = report.createContext();
		
		ctx.put("segnalazione", segnalazione);

		/*******************************************/
	    
		OutputStream out = new FileOutputStream(filePath);
		report.convert(ctx, options, out);
		out.close();		
		
	    InputStream inputStream = new FileInputStream(new File(filePath));
	    InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentLength(Files.size(Paths.get(filePath)));
	    
	    return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
	}
}
