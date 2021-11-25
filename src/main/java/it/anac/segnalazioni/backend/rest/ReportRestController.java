package it.anac.segnalazioni.backend.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.json.JSONObject;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@RestController
@RequestMapping(path="/ws")
public class ReportRestController
{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/report", method = RequestMethod.GET)	
	public ResponseEntity<InputStreamResource> download(
			@RequestParam(defaultValue = "") String id) throws IOException, XDocReportException {
		
	    String filePath = System.getProperty("java.io.tmpdir")+"/out"+id+"-"+System.currentTimeMillis()+".pdf";
		
		File initialFile = new File("tmpl.odt");
	    InputStream in = new FileInputStream(initialFile);

		IXDocReport report = XDocReportRegistry.getRegistry().
		        loadReport(in, TemplateEngineKind.Freemarker);
		
		Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

		IContext ctx = report.createContext();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		JSONObject res = mongoTemplate.findOne(query,JSONObject.class, "submissions");
		
		String nome = res.toString();
		String cognome = res.toString();
		
		System.out.println("NOME: "+nome);
		System.out.println("COGNOME: "+cognome);
		
		ctx.put("nome", nome);
		ctx.put("cognome", cognome);
	    
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
