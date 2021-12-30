package it.anac.segnalazioni.backend.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.freemarker.FreemarkerTemplateEngine;
import freemarker.template.Configuration;

public class ReportHelperPdf {
	
	public String getPdfReport(String json, String filename) throws ParseException, IOException, XDocReportException
	{
		String filePath = System.getProperty("java.io.tmpdir")+File.separatorChar+filename;
					    
		ReportHelperJson rhj = new ReportHelperJson(json);
		String area = rhj.getTipoSegnalazione();
	    boolean appalto = area.equals("appalti");
		boolean corruzione = area.equals("anticorruzione");
		boolean incarichi = area.equals("incarichi");
		boolean trasparenza = area.equals("trasparenza");
	
		// TODO definire quando è RPCT
		boolean rpct = false;
	    
	    String template_file = "";
	    if (appalto)
	    	template_file = "template_appalti.odt";
	    else if (corruzione)
	    	template_file = "template_corruzione.odt";
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
			ReportHelperAppalti reportHelper = new ReportHelperAppalti(json);
			ctx.put("segnalazione", reportHelper.createAppaltoFromJson());
		} else if (corruzione)
		{
			ReportHelperCorruzione reportHelper = new ReportHelperCorruzione(json);
			ctx.put("segnalazione", reportHelper.createAppaltoFromJson());	
		} else if (incarichi)
		{
			
		} else if (rpct)
		{
			
		} else if (trasparenza)
		{

		}
		
		OutputStream out = new FileOutputStream(filePath);
		report.convert(ctx, options, out);
		out.close();
		
		return filePath;
	}

}
