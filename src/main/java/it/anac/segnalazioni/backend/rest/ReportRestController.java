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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import fr.opensagres.xdocreport.template.freemarker.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import it.anac.segnalazioni.backend.report.util.ReportHelperAppalti;
import it.anac.segnalazioni.backend.report.util.ReportHelperCorruzione;

@RestController
@RequestMapping(path="/ws")
public class ReportRestController
{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@CrossOrigin(origins = {"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local","http://localhost:4200"})
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
		

		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
				TemplateEngineKind.Freemarker);

		FreemarkerTemplateEngine templateEngine = (FreemarkerTemplateEngine) report.getTemplateEngine();
		templateEngine.setFreemarkerConfiguration(cfg);
		report.setTemplateEngine(templateEngine);

		Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

		IContext ctx = report.createContext();
		
		if (appalto)
		{
			ReportHelperAppalti reportHelper = new ReportHelperAppalti(res.toString());
			ctx.put("segnalazione", reportHelper.createAppaltoFromJson());
		} else if (corruzione)
		{
			ReportHelperCorruzione reportHelper = new ReportHelperCorruzione(res.toString());
			ctx.put("segnalazione", reportHelper.createAppaltoFromJson());	
		} else if (incarichi)
		{
			
		} else if (rpct)
		{
			
		} else if (trasparenza)
		{

		}

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
