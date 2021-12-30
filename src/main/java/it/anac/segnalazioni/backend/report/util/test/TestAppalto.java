package it.anac.segnalazioni.backend.report.util.test;

/**
 * @author Giancarlo Carbone
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import it.anac.segnalazioni.backend.report.model.Allegato;
import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Rup;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneAppalto;


public class TestAppalto {

	private static String OGGETTO_SEGNALAZIONE = ""
			+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tincidunt ut lacus in vestibulum. "
			+ "In at dolor vehicula, consequat metus aliquam, accumsan libero. Mauris condimentum lorem sed tellus mollis, "
			+ "nec placerat nisl cursus. Donec aliquet quam diam. Aliquam bibendum volutpat imperdiet. Morbi at enim ipsum. "
			+ "Cras non sapien ut lectus ornare malesuada sagittis eget risus. Duis eu aliquet dolor. "
			+ "Ut et imperdiet diam. Praesent vitae dolor enim. Pellentesque a enim non erat dapibus suscipit sed ut orci. "
			+ "Donec eget porttitor dui, eu lobortis mi. Duis tincidunt ultricies rutrum. Nam luctus tempus condimentum. "
			+ "Suspendisse eleifend id enim egestas tempus.\r\n" + "\r\n"
			+ "In hac habitasse platea dictumst. Etiam dignissim felis dolor, sed dictum lectus tempus id. Proin nec ligula id "
			+ "libero lobortis fermentum a et risus. Fusce non molestie orci. Curabitur ultricies risus quis dui aliquet congue. "
			+ "Maecenas scelerisque ligula eget malesuada eleifend. Vivamus ut nibh sit amet leo varius luctus. Cras consequat "
			+ "purus nec urna rutrum, nec sollicitudin libero lacinia. Nulla sapien libero, lobortis a pulvinar eget, "
			+ "pulvinar ut orci. Donec non lacus vel nulla convallis molestie sit amet at enim. Quisque vestibulum diam id elit "
			+ "ultricies rutrum. ";

	private static String templateFilename = "template_appalti.odt";

	public static void main(String[] args) throws Exception {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		
		Segnalante segnalante = new Segnalante("Giancarlo", "Carbone", "CRBGCR68B20F839U", "RPCT");

		Organizzazione org = new Organizzazione("Comune di Bugliano", "Toscana", "Pisa", "Bugliano");
		org.setTipoEnte("Comune");
		org.setMail("prova@bugliano.gov.it");
		org.setPec("protocollo@bugliano.pec.it");
		org.setTelefono("+39 06060606");
		segnalante.setEnte(org);

		Organizzazione sa = new Organizzazione("Comune di Bugliano", "Toscana", "Pisa", "Bugliano");
		sa.setTipoEnte("Soggetto aggregatore");

		SegnalazioneAppalto segnalazione = new SegnalazioneAppalto(segnalante, OGGETTO_SEGNALAZIONE, 
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

		InputStream in = new FileInputStream(new File(templateFilename));

		OutputStream out = new FileOutputStream("test_" + TestAppalto.class.getSimpleName() + "_" +
				System.currentTimeMillis() + ".pdf");

		PdfBuilder.buildPdf(in, out, segnalazione);
		System.out.println("END");
	}

}
