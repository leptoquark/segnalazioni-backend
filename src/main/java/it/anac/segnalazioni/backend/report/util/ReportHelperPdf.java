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
import it.anac.segnalazioni.backend.report.model.Segnalazione;
import it.anac.segnalazioni.backend.report.model.SegnalazioneAppalto;

public class ReportHelperPdf {
	
	public String jsonTest =  
			"{\r\n" + 
			"	\"owner\" : null,\r\n" + 
			"	\"deleted\" : null,\r\n" + 
			"	\"roles\" : [ ],\r\n" + 
			"	\"data\" : {\r\n" + 
			"		\"nome_soggetto_segnalante\" : \"Claudio\",\r\n" + 
			"		\"cognome_soggetto_segnalante\" : \"Biancalana\",\r\n" + 
			"		\"codiceFiscale_soggetto_segnalante\" : \"BNCCLD79Po7H501P\",\r\n" + 
			"		\"email_soggetto_segnalante\" : \"c.biancalana@anticorruzione.it\",\r\n" + 
			"		\"qualifica\" : \"dipendente\",\r\n" + 
			"		\"tipoDocumento\" : \"cartaDiIdentita\",\r\n" + 
			"		\"numero_documento\" : \"AL 302 AH\",\r\n" + 
			"		\"documento_fronte\" : [\r\n" + 
			"			{\r\n" + 
			"				\"storage\" : \"url\",\r\n" + 
			"				\"name\" : \"Polizza_N_0000091642384-35d4651f-3d78-4b04-8f91-429b85fb298a.pdf\",\r\n" + 
			"				\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F60c0896bded6195ed0edc06190ebf6eb\",\r\n" + 
			"				\"size\" : 629281,\r\n" + 
			"				\"type\" : \"application/pdf\",\r\n" + 
			"				\"data\" : {\r\n" + 
			"					\"fieldname\" : \"file\",\r\n" + 
			"					\"originalname\" : \"Polizza_N_0000091642384.pdf\",\r\n" + 
			"					\"encoding\" : \"7bit\",\r\n" + 
			"					\"mimetype\" : \"application/pdf\",\r\n" + 
			"					\"destination\" : \"/tmp\",\r\n" + 
			"					\"filename\" : \"60c0896bded6195ed0edc06190ebf6eb\",\r\n" + 
			"					\"path\" : \"/tmp/60c0896bded6195ed0edc06190ebf6eb\",\r\n" + 
			"					\"size\" : 629281,\r\n" + 
			"					\"url\" : \"/%2F60c0896bded6195ed0edc06190ebf6eb\",\r\n" + 
			"					\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"					\"project\" : \"\",\r\n" + 
			"					\"form\" : \"\"\r\n" + 
			"				},\r\n" + 
			"				\"originalName\" : \"Polizza_N_0000091642384.pdf\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"documento_retro\" : [\r\n" + 
			"			{\r\n" + 
			"				\"storage\" : \"url\",\r\n" + 
			"				\"name\" : \"Modulo_CAI-dd3fbece-f81d-4032-a0d6-890b5c018613.pdf\",\r\n" + 
			"				\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F1594f75d051c548e5933f0357f9887fe\",\r\n" + 
			"				\"size\" : 2351818,\r\n" + 
			"				\"type\" : \"application/pdf\",\r\n" + 
			"				\"data\" : {\r\n" + 
			"					\"fieldname\" : \"file\",\r\n" + 
			"					\"originalname\" : \"Modulo_CAI.pdf\",\r\n" + 
			"					\"encoding\" : \"7bit\",\r\n" + 
			"					\"mimetype\" : \"application/pdf\",\r\n" + 
			"					\"destination\" : \"/tmp\",\r\n" + 
			"					\"filename\" : \"1594f75d051c548e5933f0357f9887fe\",\r\n" + 
			"					\"path\" : \"/tmp/1594f75d051c548e5933f0357f9887fe\",\r\n" + 
			"					\"size\" : 2351818,\r\n" + 
			"					\"url\" : \"/%2F1594f75d051c548e5933f0357f9887fe\",\r\n" + 
			"					\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"					\"project\" : \"\",\r\n" + 
			"					\"form\" : \"\"\r\n" + 
			"				},\r\n" + 
			"				\"originalName\" : \"Modulo_CAI.pdf\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"denominazione_amministrazione2\" : \"roma tre\",\r\n" + 
			"		\"selezione_ente2\" : {\r\n" + 
			"			\"id\" : \"5dcc330d32030e5709ea18a0\",\r\n" + 
			"			\"dati_identificativi\" : {\r\n" + 
			"				\"codice_fiscale_jammed\" : \"fb45acb3655473f61dc3e528fc6d5613\",\r\n" + 
			"				\"codice_fiscale\" : \"97886240585\",\r\n" + 
			"				\"partita_iva\" : \"00000000000\",\r\n" + 
			"				\"denominazione\" : \"FONDAZIONE UNIVERSITA' DEGLI STUDI ROMA TRE-EDUCATION\",\r\n" + 
			"				\"natura_giuridica\" : {\r\n" + 
			"					\"codice\" : \"09\",\r\n" + 
			"					\"descrizione\" : \"FONDAZIONI\"\r\n" + 
			"				},\r\n" + 
			"				\"soggetto_estero\" : \"N\",\r\n" + 
			"				\"localizzazione\" : {\r\n" + 
			"					\"provincia\" : {\r\n" + 
			"						\"codice\" : \"IT-RM\",\r\n" + 
			"						\"nome\" : \"ROMA\"\r\n" + 
			"					},\r\n" + 
			"					\"citta\" : {\r\n" + 
			"						\"codice\" : \"058091\",\r\n" + 
			"						\"nome\" : \"ROMA\"\r\n" + 
			"					},\r\n" + 
			"					\"indirizzo\" : {\r\n" + 
			"						\"dug\" : \"\",\r\n" + 
			"						\"odonimo\" : \"PIAZZA DELLA REPUBBLICA 10\",\r\n" + 
			"						\"numero_civico\" : \"\",\r\n" + 
			"						\"esponente\" : \"\"\r\n" + 
			"					},\r\n" + 
			"					\"cap\" : \"00185\",\r\n" + 
			"					\"tipo_sede\" : null,\r\n" + 
			"					\"georef\" : {\r\n" + 
			"						\"lat\" : 0,\r\n" + 
			"						\"lon\" : 0\r\n" + 
			"					}\r\n" + 
			"				},\r\n" + 
			"				\"cciaa\" : null,\r\n" + 
			"				\"contatti\" : {\r\n" + 
			"					\"MAIL_PEC\" : \"\",\r\n" + 
			"					\"EMAIL\" : \"\",\r\n" + 
			"					\"TELEFONO\" : null\r\n" + 
			"				}\r\n" + 
			"			},\r\n" + 
			"			\"_check_ValPar\" : \"check_ValPar\",\r\n" + 
			"			\"_source\" : \"VAL\",\r\n" + 
			"			\"documento\" : {\r\n" + 
			"				\"tipo\" : \"personaGiuridica\",\r\n" + 
			"				\"versione\" : \"1.1\"\r\n" + 
			"			},\r\n" + 
			"			\"_acl\" : [ \"pubblico\" ],\r\n" + 
			"			\"ts\" : \"Tue Jun 01 23:26:01 CEST 2021\",\r\n" + 
			"			\"tipoSoggetto\" : {\r\n" + 
			"				\"tipo_soggetto\" : \"Stazione appaltante\",\r\n" + 
			"				\"flag_inHouse\" : \"\",\r\n" + 
			"				\"flag_partecipata\" : \"\",\r\n" + 
			"				\"flag_consorziata\" : \"\"\r\n" + 
			"			},\r\n" + 
			"			\"stato\" : {\r\n" + 
			"				\"stato\" : \"\",\r\n" + 
			"				\"data_inizio\" : \"\",\r\n" + 
			"				\"data_fine\" : \"\"\r\n" + 
			"			},\r\n" + 
			"			\"classificazioni\" : [\r\n" + 
			"				{\r\n" + 
			"					\"codice_codifica\" : \"CEA1\",\r\n" + 
			"					\"codifica\" : \"Classificazione Economica ANAC\",\r\n" + 
			"					\"classificazione\" : [\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"B999-B904-B999\",\r\n" + 
			"							\"descrizione\" : \"ALTRO-NO PROFIT-NON DEFINITO\"\r\n" + 
			"						}\r\n" + 
			"					]\r\n" + 
			"				}\r\n" + 
			"			],\r\n" + 
			"			\"ruoli\" : {\r\n" + 
			"				\"rappresentanti_legali\" : null,\r\n" + 
			"				\"direttori_tecnici\" : [ ],\r\n" + 
			"				\"soggetti\" : [ ]\r\n" + 
			"			},\r\n" + 
			"			\"componenti\" : [ ]\r\n" + 
			"		},\r\n" + 
			"		\"summary_denominazione2\" : \"<ul class='list-group list-group-flush'><li class='list-group-item list-group-item-primary'><b>Denominazione:</b> FONDAZIONE UNIVERSITA' DEGLI STUDI ROMA TRE-EDUCATION</li><li class='list-group-item list-group-item-primary'><b>Codice Fiscale:</b> 97886240585</li><li class='list-group-item list-group-item-primary'><b>Localizzazione:</b> ROMA (ROMA)</li><li class='list-group-item list-group-item-primary'><b>Natura giuridica:</b> FONDAZIONI</li></ul>\",\r\n" + 
			"		\"cf_amministrazione2\" : \"\",\r\n" + 
			"		\"summary_cf2\" : \"\",\r\n" + 
			"		\"denominazione\" : \"FONDAZIONE UNIVERSITA' DEGLI STUDI ROMA TRE-EDUCATION\",\r\n" + 
			"		\"cf\" : \"97886240585\",\r\n" + 
			"		\"tipologia_ente_amministrazione\" : \"universita\",\r\n" + 
			"		\"regione\" : \"Lazio\",\r\n" + 
			"		\"provincia\" : \"Roma\",\r\n" + 
			"		\"comune\" : \"Roma\",\r\n" + 
			"		\"mail\" : \"ufficio.anticorruzione@uniroma3.it\",\r\n" + 
			"		\"pec\" : \"claidio.biancalana@pec.it\",\r\n" + 
			"		\"telefono\" : 12345678900099,\r\n" + 
			"		\"area\" : \"appalti\",\r\n" + 
			"		\"cig\" : \"0000000000\",\r\n" + 
			"		\"denominazione_sa\" : \"AZIENDA USL VITERBO\",\r\n" + 
			"		\"codiceFiscale_sa\" : \"01455570562\",\r\n" + 
			"		\"regione_appalti\" : \"Lazio\",\r\n" + 
			"		\"provincia_appalti\" : \"Viterbo\",\r\n" + 
			"		\"comune_appalti\" : \"Viterbo\",\r\n" + 
			"		\"stazioneappaltante\" : \"committenza\",\r\n" + 
			"		\"denominazione_operatoreEconomico\" : \"denominazione dell'operatore economico\",\r\n" + 
			"		\"codiceFiscale_operatoreEconomico\" : \"12345678780\",\r\n" + 
			"		\"cig_aggiuntivi\" : [\r\n" + 
			"			{\r\n" + 
			"				\"codiceIdentificativoGaraCig\" : \"0000000000\"\r\n" + 
			"			},\r\n" + 
			"			{\r\n" + 
			"				\"codiceIdentificativoGaraCig\" : \"0000000000\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"ambitodellintervento\" : \"serviziDiIngegneriaEdArchitettura\",\r\n" + 
			"		\"oggettoContratto_sa\" : \"campo per l'oggetto del contratto\",\r\n" + 
			"		\"procedura_affidamento\" : \"proceduraNegoziata\",\r\n" + 
			"		\"faseprocedura\" : \"collaudo\",\r\n" + 
			"		\"data_collaudo\" : \"2021-12-29T00:00:00.000+01:00\",\r\n" + 
			"		\"importo_base_asta\" : 100000,\r\n" + 
			"		\"nome_rup\" : \"SIMONA\",\r\n" + 
			"		\"cognome_rup\" : \"DI GIOVANNI\",\r\n" + 
			"		\"descrizione_intervento_segnalazione\" : \"LOTTO 24 J & J\",\r\n" + 
			"		\"PeriodoPresuntaSegnalazione\" : \"\",\r\n" + 
			"		\"appalti_end\" : {\r\n" + 
			"			\"contenziosoSegnalante\" : false\r\n" + 
			"		},\r\n" + 
			"		\"soggettiaux\" : {\r\n" + 
			"			\"rpct\" : true,\r\n" + 
			"			\"procuraDellaRepubblica\" : true,\r\n" + 
			"			\"procuraRegionaleCorteDeiConti\" : true,\r\n" + 
			"			\"ispettoratoPerLaFunzionePubblica\" : true,\r\n" + 
			"			\"prefettura\" : false,\r\n" + 
			"			\"altro\" : false\r\n" + 
			"		},\r\n" + 
			"		\"documenti_allegati_chiusura\" : [ ],\r\n" + 
			"		\"dati_sensibili\" : \"\"\r\n" + 
			"	},\r\n" + 
			"	\"access\" : [ ],\r\n" + 
			"	\"metadata\" : {\r\n" + 
			"		\"timezone\" : \"Europe/Rome\",\r\n" + 
			"		\"offset\" : 60,\r\n" + 
			"		\"origin\" : \"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"		\"referrer\" : \"\",\r\n" + 
			"		\"browserName\" : \"Netscape\",\r\n" + 
			"		\"userAgent\" : \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36\",\r\n" + 
			"		\"pathName\" : \"/form\",\r\n" + 
			"		\"onLine\" : true,\r\n" + 
			"		\"headers\" : {\r\n" + 
			"			\"content-length\" : \"6206\",\r\n" + 
			"			\"accept\" : \"application/json\",\r\n" + 
			"			\"user-agent\" : \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36\",\r\n" + 
			"			\"content-type\" : \"application/json\",\r\n" + 
			"			\"origin\" : \"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"			\"referer\" : \"http://segnalazioni-segnalazioni-ril.apps.ocp.premaster.local/\",\r\n" + 
			"			\"accept-encoding\" : \"gzip, deflate\",\r\n" + 
			"			\"accept-language\" : \"it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7\",\r\n" + 
			"			\"host\" : \"nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"			\"x-forwarded-host\" : \"nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"			\"x-forwarded-port\" : \"80\",\r\n" + 
			"			\"x-forwarded-proto\" : \"http\",\r\n" + 
			"			\"forwarded\" : \"for=10.130.72.235;host=nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local;proto=http\",\r\n" + 
			"			\"x-forwarded-for\" : \"10.130.72.235\"\r\n" + 
			"		}\r\n" + 
			"	},\r\n" + 
			"	\"externalIds\" : [ ],\r\n" + 
			"	\"created\" : \"2021-12-29T13:32:54.751+01:00\",\r\n" + 
			"	\"modified\" : \"2021-12-29T13:32:54.753+01:00\",\r\n" + 
			"	\"__v\" : 0\r\n" + 
			"}";
	
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
	
	public static void main(String[] argv) throws ParseException, IOException, XDocReportException
	{
		ReportHelperPdf rhp = new ReportHelperPdf();
		System.out.println(rhp.getPdfReport(rhp.jsonTest, "test.pdf"));
	}

}
