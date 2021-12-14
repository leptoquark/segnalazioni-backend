package it.anac.segnalazioni.backend.report.util;

/**
 * @author Giancarlo Carbone
 *
 */
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.freemarker.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import it.anac.segnalazioni.backend.report.model.Segnalazione;

public class PdfBuilder {

	public static void buildPdf(InputStream templateInStream, OutputStream pdfOutputStream, Segnalazione segnalazione)
			throws Exception {

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		// cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(java.util.Locale.ITALIAN);
		cfg.setDateFormat("d MMMMM yyyy");
		cfg.setTemplateExceptionHandler(new MyTemplateExceptionHandler());

		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(templateInStream,
				TemplateEngineKind.Freemarker);

		FreemarkerTemplateEngine templateEngine = (FreemarkerTemplateEngine) report.getTemplateEngine();
		templateEngine.setFreemarkerConfiguration(cfg);
		report.setTemplateEngine(templateEngine);

		Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);

		IContext ctx = report.createContext();

		// Add Java Object to the context
		ctx.put("segnalazione", segnalazione);

		report.convert(ctx, options, pdfOutputStream);
		pdfOutputStream.close();

	}

}
