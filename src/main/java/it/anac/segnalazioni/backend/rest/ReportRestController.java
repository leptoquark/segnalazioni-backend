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
	private String jsonTest = 
			"{\r\n" + 
			"	\"data\" : {\r\n" + 
			"		\"nome_soggetto_segnalante\" : \"Claudio\",\r\n" + 
			"		\"cognome_soggetto_segnalante\" : \"Biancalana\",\r\n" + 
			"		\"codiceFiscale_soggetto_segnalante\" : \"BNCCLD79P07H501P\",\r\n" + 
			"		\"email_soggetto_segnalante\" : \"claudio.biancalana@gmail.com\",\r\n" + 
			"		\"qualifica\" : \"dipendente\",\r\n" + 
			"		\"tipoDocumento\" : \"cartaDiIdentita\",\r\n" + 
			"		\"numero_documento\" : \"AL 302 AH\",\r\n" + 
			"		\"documento_fronte\" : [\r\n" + 
			"			{\r\n" + 
			"				\"storage\" : \"url\",\r\n" + 
			"				\"name\" : \"Modulo_CAI-9b8203d4-9a14-4ad8-8f87-0e5aa10f6210.pdf\",\r\n" + 
			"				\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2Ff3cfaf00bf5edfbdbe0cca1eb5aab024\",\r\n" + 
			"				\"size\" : 2351818,\r\n" + 
			"				\"type\" : \"application/pdf\",\r\n" + 
			"				\"data\" : {\r\n" + 
			"					\"fieldname\" : \"file\",\r\n" + 
			"					\"originalname\" : \"Modulo_CAI.pdf\",\r\n" + 
			"					\"encoding\" : \"7bit\",\r\n" + 
			"					\"mimetype\" : \"application/pdf\",\r\n" + 
			"					\"destination\" : \"/tmp\",\r\n" + 
			"					\"filename\" : \"f3cfaf00bf5edfbdbe0cca1eb5aab024\",\r\n" + 
			"					\"path\" : \"/tmp/f3cfaf00bf5edfbdbe0cca1eb5aab024\",\r\n" + 
			"					\"size\" : 2351818,\r\n" + 
			"					\"url\" : \"/%2Ff3cfaf00bf5edfbdbe0cca1eb5aab024\",\r\n" + 
			"					\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"					\"project\" : \"\",\r\n" + 
			"					\"form\" : \"\"\r\n" + 
			"				},\r\n" + 
			"				\"originalName\" : \"Modulo_CAI.pdf\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"documento_retro\" : [\r\n" + 
			"			{\r\n" + 
			"				\"storage\" : \"url\",\r\n" + 
			"				\"name\" : \"Polizza_N_0000091642384-8306b754-696e-4c96-9b23-1753d91ae007.pdf\",\r\n" + 
			"				\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F4a8beaa00ba17f87b04309b3910bfc8a\",\r\n" + 
			"				\"size\" : 629281,\r\n" + 
			"				\"type\" : \"application/pdf\",\r\n" + 
			"				\"data\" : {\r\n" + 
			"					\"fieldname\" : \"file\",\r\n" + 
			"					\"originalname\" : \"Polizza_N_0000091642384.pdf\",\r\n" + 
			"					\"encoding\" : \"7bit\",\r\n" + 
			"					\"mimetype\" : \"application/pdf\",\r\n" + 
			"					\"destination\" : \"/tmp\",\r\n" + 
			"					\"filename\" : \"4a8beaa00ba17f87b04309b3910bfc8a\",\r\n" + 
			"					\"path\" : \"/tmp/4a8beaa00ba17f87b04309b3910bfc8a\",\r\n" + 
			"					\"size\" : 629281,\r\n" + 
			"					\"url\" : \"/%2F4a8beaa00ba17f87b04309b3910bfc8a\",\r\n" + 
			"					\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"					\"project\" : \"\",\r\n" + 
			"					\"form\" : \"\"\r\n" + 
			"				},\r\n" + 
			"				\"originalName\" : \"Polizza_N_0000091642384.pdf\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"denominazione_amministrazione2\" : \"lazio\",\r\n" + 
			"		\"selezione_ente2\" : {\r\n" + 
			"			\"id\" : \"5dc5995932030e57099971a7\",\r\n" + 
			"			\"dati_identificativi\" : {\r\n" + 
			"				\"codice_fiscale_jammed\" : \"92c0c22d7afe0124640847b9a894a5e1\",\r\n" + 
			"				\"codice_fiscale\" : \"01134160769\",\r\n" + 
			"				\"partita_iva\" : \"01134160769\",\r\n" + 
			"				\"denominazione\" : \"2D TRIVELLAZIONI S.N.C. DI DE CARLO GERARDO & F.LLI\",\r\n" + 
			"				\"natura_giuridica\" : {\r\n" + 
			"					\"codice\" : \"24\",\r\n" + 
			"					\"descrizione\" : \"SOCIETA' IN NOME COLLETTIVO\"\r\n" + 
			"				},\r\n" + 
			"				\"soggetto_estero\" : \"N\",\r\n" + 
			"				\"localizzazione\" : {\r\n" + 
			"					\"provincia\" : {\r\n" + 
			"						\"codice\" : \"IT-PZ\",\r\n" + 
			"						\"nome\" : \"POTENZA\"\r\n" + 
			"					},\r\n" + 
			"					\"citta\" : {\r\n" + 
			"						\"codice\" : \"076071\",\r\n" + 
			"						\"nome\" : \"RUOTI\"\r\n" + 
			"					},\r\n" + 
			"					\"indirizzo\" : {\r\n" + 
			"						\"dug\" : \"CONTRADA\",\r\n" + 
			"						\"odonimo\" : \"BOSCO GRANDE\",\r\n" + 
			"						\"numero_civico\" : \"73\",\r\n" + 
			"						\"esponente\" : \"\"\r\n" + 
			"					},\r\n" + 
			"					\"cap\" : \"85056\",\r\n" + 
			"					\"tipo_sede\" : null,\r\n" + 
			"					\"georef\" : {\r\n" + 
			"						\"lat\" : 0,\r\n" + 
			"						\"lon\" : 0\r\n" + 
			"					}\r\n" + 
			"				},\r\n" + 
			"				\"cciaa\" : {\r\n" + 
			"					\"numero_iscrizione_CCIAA\" : \"81092\",\r\n" + 
			"					\"provincia_iscrizione_CCIAA\" : \"POTENZA\",\r\n" + 
			"					\"data_iscrizione_CCIAA\" : \"1993-06-08\"\r\n" + 
			"				},\r\n" + 
			"				\"contatti\" : {\r\n" + 
			"					\"MAIL_PEC\" : \"2DTRIVELLAZIONISNC@PEC.IT\",\r\n" + 
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
			"			\"ts\" : \"Wed Jun 02 05:04:41 CEST 2021\",\r\n" + 
			"			\"tipoSoggetto\" : {\r\n" + 
			"				\"tipo_soggetto\" : \"Impresa\",\r\n" + 
			"				\"flag_inHouse\" : \"\",\r\n" + 
			"				\"flag_partecipata\" : \"\",\r\n" + 
			"				\"flag_consorziata\" : \"\"\r\n" + 
			"			},\r\n" + 
			"			\"stato\" : {\r\n" + 
			"				\"stato\" : \"ATTIVA\",\r\n" + 
			"				\"data_inizio\" : \"1993-06-08\",\r\n" + 
			"				\"data_fine\" : \"\"\r\n" + 
			"			},\r\n" + 
			"			\"classificazioni\" : [\r\n" + 
			"				{\r\n" + 
			"					\"codice_codifica\" : \"07\",\r\n" + 
			"					\"codifica\" : \"Classificazione ATECORI 2007\",\r\n" + 
			"					\"classificazione\" : [\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"41.2\",\r\n" + 
			"							\"descrizione\" : \"Costruzione di edifici residenziali e non residenziali\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"42.11\",\r\n" + 
			"							\"descrizione\" : \"Costruzione di strade, autostrade e piste aeroportuali\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"42.21\",\r\n" + 
			"							\"descrizione\" : \"Costruzione di opere di pubblica utilita' per il trasporto di fluidi\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"42.91\",\r\n" + 
			"							\"descrizione\" : \"Costruzione di opere idrauliche\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.11\",\r\n" + 
			"							\"descrizione\" : \"Demolizione di edifici\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.13\",\r\n" + 
			"							\"descrizione\" : \"Trivellazioni e perforazioni\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.29.02\",\r\n" + 
			"							\"descrizione\" : \"Lavori di isolamento termico, acustico o antivibrazioni\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.29.09\",\r\n" + 
			"							\"descrizione\" : \"Altri lavori di costruzione e installazione nca\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.31\",\r\n" + 
			"							\"descrizione\" : \"Intonacatura e stuccatura\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.32\",\r\n" + 
			"							\"descrizione\" : \"Posa in opera di casseforti ed infissi\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.34\",\r\n" + 
			"							\"descrizione\" : \"Tinteggiatura e posa in opera di vetri\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.91\",\r\n" + 
			"							\"descrizione\" : \"Realizzazione di coperture\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"43.99\",\r\n" + 
			"							\"descrizione\" : \"Altri lavori specializzati di costruzione nca\"\r\n" + 
			"						},\r\n" + 
			"						{\r\n" + 
			"							\"codice\" : \"90.03.02\",\r\n" + 
			"							\"descrizione\" : \"Attivita' di conservazione e restauro di opere d'arte\"\r\n" + 
			"						}\r\n" + 
			"					]\r\n" + 
			"				}\r\n" + 
			"			],\r\n" + 
			"			\"ruoli\" : {\r\n" + 
			"				\"rappresentanti_legali\" : {\r\n" + 
			"					\"rl_persone_giuridiche\" : [ ],\r\n" + 
			"					\"rl_persone_fisiche\" : [ ]\r\n" + 
			"				},\r\n" + 
			"				\"direttori_tecnici\" : [\r\n" + 
			"					{\r\n" + 
			"						\"data_inizio_rapporto\" : \"2003-01-02\",\r\n" + 
			"						\"data_fine_rapporto\" : null,\r\n" + 
			"						\"tipo_rapporto_direttore_tecnico\" : {\r\n" + 
			"							\"codice\" : \"\",\r\n" + 
			"							\"descrizione\" : \"\"\r\n" + 
			"						},\r\n" + 
			"						\"persona_fisica\" : {\r\n" + 
			"							\"dati_identificativi\" : {\r\n" + 
			"								\"codice_fiscale\" : \"DCRPTR49S22H641W\",\r\n" + 
			"								\"nome\" : \"PIETRO\",\r\n" + 
			"								\"cognome\" : \"DE CARLO\",\r\n" + 
			"								\"contatti\" : {\r\n" + 
			"									\"MAIL_PEC\" : \"\",\r\n" + 
			"									\"EMAIL\" : \"\",\r\n" + 
			"									\"TELEFONO\" : null\r\n" + 
			"								}\r\n" + 
			"							},\r\n" + 
			"							\"_check_ValPar\" : null,\r\n" + 
			"							\"_source\" : null,\r\n" + 
			"							\"documento\" : {\r\n" + 
			"								\"tipo\" : \"personaFisica\",\r\n" + 
			"								\"versione\" : \"1.0\"\r\n" + 
			"							},\r\n" + 
			"							\"_acl\" : null,\r\n" + 
			"							\"ts\" : \"Tue Dec 29 23:08:25 CET 2020\"\r\n" + 
			"						}\r\n" + 
			"					},\r\n" + 
			"					{\r\n" + 
			"						\"data_inizio_rapporto\" : \"2003-01-02\",\r\n" + 
			"						\"data_fine_rapporto\" : null,\r\n" + 
			"						\"tipo_rapporto_direttore_tecnico\" : {\r\n" + 
			"							\"codice\" : \"\",\r\n" + 
			"							\"descrizione\" : \"\"\r\n" + 
			"						},\r\n" + 
			"						\"persona_fisica\" : {\r\n" + 
			"							\"dati_identificativi\" : {\r\n" + 
			"								\"codice_fiscale\" : \"DCRRCC58B16H641A\",\r\n" + 
			"								\"nome\" : \"ROCCO\",\r\n" + 
			"								\"cognome\" : \"DE CARLO\",\r\n" + 
			"								\"contatti\" : {\r\n" + 
			"									\"MAIL_PEC\" : \"\",\r\n" + 
			"									\"EMAIL\" : \"ROCCO.DECARLO58@TISCALI.IT\",\r\n" + 
			"									\"TELEFONO\" : null\r\n" + 
			"								}\r\n" + 
			"							},\r\n" + 
			"							\"_check_ValPar\" : null,\r\n" + 
			"							\"_source\" : null,\r\n" + 
			"							\"documento\" : {\r\n" + 
			"								\"tipo\" : \"personaFisica\",\r\n" + 
			"								\"versione\" : \"1.0\"\r\n" + 
			"							},\r\n" + 
			"							\"_acl\" : null,\r\n" + 
			"							\"ts\" : \"Thu Dec 31 02:10:30 CET 2020\"\r\n" + 
			"						}\r\n" + 
			"					},\r\n" + 
			"					{\r\n" + 
			"						\"data_inizio_rapporto\" : \"2003-01-02\",\r\n" + 
			"						\"data_fine_rapporto\" : null,\r\n" + 
			"						\"tipo_rapporto_direttore_tecnico\" : {\r\n" + 
			"							\"codice\" : \"\",\r\n" + 
			"							\"descrizione\" : \"\"\r\n" + 
			"						},\r\n" + 
			"						\"persona_fisica\" : {\r\n" + 
			"							\"dati_identificativi\" : {\r\n" + 
			"								\"codice_fiscale\" : \"DCRRCC58B16H641A\",\r\n" + 
			"								\"nome\" : \"ROCCO\",\r\n" + 
			"								\"cognome\" : \"DE CARLO\",\r\n" + 
			"								\"contatti\" : {\r\n" + 
			"									\"MAIL_PEC\" : \"\",\r\n" + 
			"									\"EMAIL\" : \"ROCCO.DECARLO58@TISCALI.IT\",\r\n" + 
			"									\"TELEFONO\" : null\r\n" + 
			"								}\r\n" + 
			"							},\r\n" + 
			"							\"_check_ValPar\" : null,\r\n" + 
			"							\"_source\" : null,\r\n" + 
			"							\"documento\" : {\r\n" + 
			"								\"tipo\" : \"personaFisica\",\r\n" + 
			"								\"versione\" : \"1.0\"\r\n" + 
			"							},\r\n" + 
			"							\"_acl\" : null,\r\n" + 
			"							\"ts\" : \"Thu Dec 31 02:10:30 CET 2020\"\r\n" + 
			"						}\r\n" + 
			"					}\r\n" + 
			"				],\r\n" + 
			"				\"soggetti\" : [ ]\r\n" + 
			"			},\r\n" + 
			"			\"componenti\" : [ ]\r\n" + 
			"		},\r\n" + 
			"		\"summary_denominazione2\" : \"<ul class='list-group list-group-flush'><li class='list-group-item list-group-item-primary'><b>Denominazione:</b> 2D TRIVELLAZIONI S.N.C. DI DE CARLO GERARDO & F.LLI</li><li class='list-group-item list-group-item-primary'><b>Codice Fiscale:</b> 01134160769</li><li class='list-group-item list-group-item-primary'><b>Localizzazione:</b> RUOTI (POTENZA)</li><li class='list-group-item list-group-item-primary'><b>Natura giuridica:</b> SOCIETA' IN NOME COLLETTIVO</li></ul>\",\r\n" + 
			"		\"cf_amministrazione2\" : \"\",\r\n" + 
			"		\"summary_cf2\" : \"\",\r\n" + 
			"		\"denominazione\" : \"2D TRIVELLAZIONI S.N.C. DI DE CARLO GERARDO & F.LLI\",\r\n" + 
			"		\"cf\" : \"01134160769\",\r\n" + 
			"		\"tipologia_ente_amministrazione\" : \"ministero\",\r\n" + 
			"		\"regione\" : \"Basilicata\",\r\n" + 
			"		\"provincia\" : \"Potenza\",\r\n" + 
			"		\"comune\" : \"Ruoti\",\r\n" + 
			"		\"mail\" : \"massimiliano.raffa@laziocrea.it\",\r\n" + 
			"		\"pec\" : \"2dtrivellazionisnc@pec.it\",\r\n" + 
			"		\"area\" : \"appalti\",\r\n" + 
			"		\"cig\" : \"XA31927D46\",\r\n" + 
			"		\"denominazione_sa\" : \"SO.G.I.P. SRL UNIPERSONALE\",\r\n" + 
			"		\"codiceFiscale_sa\" : \"04015810874\",\r\n" + 
			"		\"regione_appalti\" : \"Sicilia\",\r\n" + 
			"		\"provincia_appalti\" : \"Catania\",\r\n" + 
			"		\"comune_appalti\" : \"Acireale\",\r\n" + 
			"		\"stazioneappaltante\" : \"\",\r\n" + 
			"		\"denominazione_operatoreEconomico\" : \"denominazione\",\r\n" + 
			"		\"codiceFiscale_operatoreEconomico\" : \"1234567890123456\",\r\n" + 
			"		\"cig_aggiuntivi\" : [\r\n" + 
			"			{\r\n" + 
			"				\"codiceIdentificativoGaraCig\" : \"0000000000\"\r\n" + 
			"			},\r\n" + 
			"			{\r\n" + 
			"				\"codiceIdentificativoGaraCig\" : \"0000000000\"\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"ambitodellintervento\" : \"appaltoDiServiziPubbliciLocali\",\r\n" + 
			"		\"oggettoContratto_sa\" : \"oggetto del contratto\",\r\n" + 
			"		\"procedura_affidamento\" : \"proceduraAperta\",\r\n" + 
			"		\"faseprocedura\" : \"garaInCorso\",\r\n" + 
			"		\"data_scadenza\" : \"2021-12-24T00:00:00.000+01:00\",\r\n" + 
			"		\"importo_base_asta\" : 12000,\r\n" + 
			"		\"nome_rup\" : \"SALVATORE\",\r\n" + 
			"		\"cognome_rup\" : \"MESSINA\",\r\n" + 
			"		\"descrizione_intervento_segnalazione\" : \"FORNITURA DI RAM AGGIUNTIVA PER SERVER CENTRALE\",\r\n" + 
			"		\"PeriodoPresuntaSegnalazione\" : \"nessuno\",\r\n" + 
			"		\"appalti_end\" : {\r\n" + 
			"			\"contenziosoSegnalante\" : true,\r\n" + 
			"			\"Segnalantepartedelgiudizio\" : \"si\",\r\n" + 
			"			\"esistenzacivile\" : true,\r\n" + 
			"			\"esistenzacivile_estremi\" : \"1\",\r\n" + 
			"			\"esistenzapenale\" : true,\r\n" + 
			"			\"esistenzapenale_estremi\" : \"2\",\r\n" + 
			"			\"esistenzaanac\" : true,\r\n" + 
			"			\"esistenzaanac_estremi\" : \"3\",\r\n" + 
			"			\"esistenzacorteconti\" : true,\r\n" + 
			"			\"esistenzacorteconti_estremi\" : \"4\",\r\n" + 
			"			\"esistenzaautotutela\" : true,\r\n" + 
			"			\"esistenzaautotutela_estremi\" : \"5\",\r\n" + 
			"			\"esistenzaamministrativo\" : true,\r\n" + 
			"			\"esistenzaamministrativo_estremi\" : \"6\"\r\n" + 
			"		},\r\n" + 
			"		\"esistenzaanacsegnalazioni\" : true,\r\n" + 
			"		\"esistenzaanacsegnalazioni_estremi\" : \"7\",\r\n" + 
			"		\"soggettiaux\" : {\r\n" + 
			"			\"rpct\" : true,\r\n" + 
			"			\"procuraDellaRepubblica\" : false,\r\n" + 
			"			\"procuraRegionaleCorteDeiConti\" : false,\r\n" + 
			"			\"ispettoratoPerLaFunzionePubblica\" : false,\r\n" + 
			"			\"prefettura\" : true,\r\n" + 
			"			\"altro\" : false\r\n" + 
			"		},\r\n" + 
			"		\"documenti_allegati_chiusura\" : [\r\n" + 
			"			{\r\n" + 
			"				\"titolo_documento\" : \"titolo\",\r\n" + 
			"				\"note_documento\" : \"note\",\r\n" + 
			"				\"documento_allegati\" : [\r\n" + 
			"					{\r\n" + 
			"						\"storage\" : \"url\",\r\n" + 
			"						\"name\" : \"ANTIRICICLAGGIO ESTESO -1--73a19dcb-5c82-4d54-b19d-bd5958881f28.pdf\",\r\n" + 
			"						\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F6f7e207d4c8c5073e4301506e9a999e5\",\r\n" + 
			"						\"size\" : 256804,\r\n" + 
			"						\"type\" : \"application/pdf\",\r\n" + 
			"						\"data\" : {\r\n" + 
			"							\"fieldname\" : \"file\",\r\n" + 
			"							\"originalname\" : \"ANTIRICICLAGGIO ESTESO (1).pdf\",\r\n" + 
			"							\"encoding\" : \"7bit\",\r\n" + 
			"							\"mimetype\" : \"application/pdf\",\r\n" + 
			"							\"destination\" : \"/tmp\",\r\n" + 
			"							\"filename\" : \"6f7e207d4c8c5073e4301506e9a999e5\",\r\n" + 
			"							\"path\" : \"/tmp/6f7e207d4c8c5073e4301506e9a999e5\",\r\n" + 
			"							\"size\" : 256804,\r\n" + 
			"							\"url\" : \"/%2F6f7e207d4c8c5073e4301506e9a999e5\",\r\n" + 
			"							\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"							\"project\" : \"\",\r\n" + 
			"							\"form\" : \"\"\r\n" + 
			"						},\r\n" + 
			"						\"originalName\" : \"ANTIRICICLAGGIO ESTESO (1).pdf\"\r\n" + 
			"					}\r\n" + 
			"				]\r\n" + 
			"			},\r\n" + 
			"			{\r\n" + 
			"				\"titolo_documento\" : \"titolo_2\",\r\n" + 
			"				\"note_documento\" : \"note\",\r\n" + 
			"				\"documento_allegati\" : [\r\n" + 
			"					{\r\n" + 
			"						\"storage\" : \"url\",\r\n" + 
			"						\"name\" : \"documento_fronte-057cf307-439e-4b87-8182-bbc4a6912ce9.pdf\",\r\n" + 
			"						\"url\" : \"http://formio-upload-segnalazioni-ril.apps.ocp.premaster.local/file/%2F5b5c9fab315322a28a0cd1307350789b\",\r\n" + 
			"						\"size\" : 89583,\r\n" + 
			"						\"type\" : \"application/pdf\",\r\n" + 
			"						\"data\" : {\r\n" + 
			"							\"fieldname\" : \"file\",\r\n" + 
			"							\"originalname\" : \"documento_fronte.pdf\",\r\n" + 
			"							\"encoding\" : \"7bit\",\r\n" + 
			"							\"mimetype\" : \"application/pdf\",\r\n" + 
			"							\"destination\" : \"/tmp\",\r\n" + 
			"							\"filename\" : \"5b5c9fab315322a28a0cd1307350789b\",\r\n" + 
			"							\"path\" : \"/tmp/5b5c9fab315322a28a0cd1307350789b\",\r\n" + 
			"							\"size\" : 89583,\r\n" + 
			"							\"url\" : \"/%2F5b5c9fab315322a28a0cd1307350789b\",\r\n" + 
			"							\"baseUrl\" : \"http://nodejs-mongodb-formio-segnalazioni-ril.apps.ocp.premaster.local\",\r\n" + 
			"							\"project\" : \"\",\r\n" + 
			"							\"form\" : \"\"\r\n" + 
			"						},\r\n" + 
			"						\"originalName\" : \"documento_fronte.pdf\"\r\n" + 
			"					}\r\n" + 
			"				]\r\n" + 
			"			}\r\n" + 
			"		],\r\n" + 
			"		\"dati_sensibili\" : \"kkk\"\r\n" + 
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
			"			\"content-length\" : \"10818\",\r\n" + 
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
			"	\"__v\" : 0\r\n" + 
			"}";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private String getValueFromJson(JsonNode nameNode, String prop)
	{
		String ret = "";
		
        if (nameNode.get(prop)!=null)
        	ret = nameNode.get(prop).asText();
        	
		return ret;
	}
	
	private int getIntValueFromJson(JsonNode nameNode, String prop)
	{
		int ret_int = 0;
		String ret = getValueFromJson(nameNode, prop);
		if (!ret.trim().equals(""))
			Integer.valueOf(ret_int);		
		return ret_int;
	}
	
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
		
		Segnalante segnalante = new Segnalante(getValueFromJson(nameNode,"nome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"cognome_soggetto_segnalante"),
											   getValueFromJson(nameNode,"codiceFiscale_soggetto_segnalante"),
											   getValueFromJson(nameNode,"qualifica"));

		Organizzazione org = new Organizzazione(getValueFromJson(nameNode,"denominazione"),
												getValueFromJson(nameNode,"regione"),
												getValueFromJson(nameNode,"provincia"),
												getValueFromJson(nameNode,"comune"));
		
		String tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione");
		if (tipoEnte.trim().equals(""))
			tipoEnte = getValueFromJson(nameNode,"tipologia_ente_amministrazione_rpct");
		org.setTipoEnte(tipoEnte);
		
		String mail =getValueFromJson(nameNode,"mail");
		if (mail.trim().equals(""))
			mail = getValueFromJson(nameNode,"mail_rpct");	
		org.setMail(mail);
		
		String pec = getValueFromJson(nameNode,"pec");
		if (pec.trim().equals(""))
			pec = getValueFromJson(nameNode,"pec_rpct");	
		org.setPec(pec);
		
		String telefono = getValueFromJson(nameNode,"telefono");
		if (telefono.trim().equals(""))
			telefono = getValueFromJson(nameNode,"telefono_rpct");	
		org.setTelefono(telefono);
		
		String cf = getValueFromJson(nameNode,"cf");
		if (telefono.trim().equals(""))
			telefono = getValueFromJson(nameNode,"cf_rpct");	
		org.setCodiceFiscale(cf);
		
		segnalante.setEnte(org);

		Organizzazione sa = new Organizzazione(
				getValueFromJson(nameNode, "denominazione_sa"),
				getValueFromJson(nameNode, "regione_appalti"),
				getValueFromJson(nameNode, "provincia_appalti"),
				getValueFromJson(nameNode, "comune_appalti"));
		sa.setTipoEnte(getValueFromJson(nameNode, "stazioneappaltante"));

		SegnalazioneAppalto segnalazione = new SegnalazioneAppalto(
													segnalante,
													getValueFromJson(nameNode, "descrizione_intervento_segnalazione"), 
													dateformat.parse("07-12-2021"),
													sa,
													"Fornitura di banchi a rotelle",
													"Appalto di Servizi/Forniture");

		// Operatore Economico
		Organizzazione oe = new Organizzazione(
				getValueFromJson(nameNode, "denominazione_operatoreEconomico"),
				getValueFromJson(nameNode, "codiceFiscale_operatoreEconomico"));
		segnalazione.setOe(oe);
		
		segnalazione.setCig(getValueFromJson(nameNode, "cig"));
		
		// Aggiungiamo ulteriori CIG
		JsonNode arrNode_cig = nameNode.get("cig_aggiuntivi");
		if (arrNode_cig.isArray()) {
		    for (JsonNode objNode : arrNode_cig) {
		        segnalazione.addCig(objNode.get("codiceIdentificativoGaraCig").asText());
		    }
		}
		
		// Secretato
		segnalazione.setSecretato(getValueFromJson(nameNode, "contrattoSecretato").equals("si"));
		segnalazione.setGenerale(getValueFromJson(nameNode, "contraenteGenerale").equals("si"));
		segnalazione.setPerSe(getValueFromJson(nameNode, "fornituraCentraleCommittenza").equals("si"));
		
		// Importo base d'asta
		segnalazione.setBaseAsta(getIntValueFromJson(nameNode, "importo_base_asta"));
		
		// Procedura di affidamento
		segnalazione.setProcedura(getValueFromJson(nameNode, "procedura_affidamento"));
		
		// Fase
		segnalazione.setFase(getValueFromJson(nameNode, "faseprocedura"));
		
		System.out.println(getValueFromJson(nameNode, "data_scadenza"));
		System.out.println(getValueFromJson(nameNode, "data_aggiudicazione"));
		System.out.println(getValueFromJson(nameNode, "data_stipula"));
		System.out.println(getValueFromJson(nameNode, "data_collaudo"));
		
		segnalazione.setDataFase(dateformat.parse("20-02-2021"));
		
		segnalazione.setImporto(getIntValueFromJson(nameNode, "importo_contrattuale"));
		
		// RUP
		segnalazione.setRup(new Rup(getValueFromJson(nameNode, "nome_rup"), getValueFromJson(nameNode, "cognome_rup")));
		
		// Data della presunta violazione
		segnalazione.setDataViolazione(getValueFromJson(nameNode, "PeriodoPresuntaSegnalazione"));
		
		// Aggiungi tutti i possibili contenziosi
		segnalazione.setContenzioso(getValueFromJson(nameNode, "contenziosoSegnalante").equals("true"));
		
		
		if (getValueFromJson(nameNode, "esistenzacivile").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.CIVILE);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzacivile_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzapenale").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.PENALE);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzapenale_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaanac").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.ANAC);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaanac_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzacorteconti").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.CORTE_CONTI);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzacorteconti_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaautotutela").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.AUTOTUTELA);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaautotutela_estremi"));
			segnalazione.addContenzioso(cont);
		}
		if (getValueFromJson(nameNode, "esistenzaamministrativo").equals("true"))
		{
			Contenzioso cont = new Contenzioso(ContenziosoType.AMMINISTRATIVO);
			cont.setEstremi(getValueFromJson(nameNode, "esistenzaamministrativo_estremi"));
			segnalazione.addContenzioso(cont);
		}
		
		// Aggiungi altre segnalazioni
		segnalazione.setAltreSegnalazioni(getValueFromJson(nameNode, "esistenzaanacsegnalazioni").equals("true"));
		segnalazione.setEstremiSegnalazioni(getValueFromJson(nameNode, "esistenzaanacsegnalazioni_estremi"));
		
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
		
		System.out.println(filePath);
	    
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
