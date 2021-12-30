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
import it.anac.segnalazioni.backend.report.model.Carenza;
import it.anac.segnalazioni.backend.report.model.Contenzioso;
import it.anac.segnalazioni.backend.report.model.ContenziosoType;
import it.anac.segnalazioni.backend.report.model.Organizzazione;
import it.anac.segnalazioni.backend.report.model.Segnalante;
import it.anac.segnalazioni.backend.report.model.SegnalazioneTrasparenza;


public class TestTrasparenza {
	
	private static String LINK_AMM_TRASP = "https://amministrazione.trasparente";
	
	// private static String ASSENZA = "Assenza sezione Amministrazione/Societ� Trasparente";
	private static String CARENZA = "Carenze sulla sezione Amministrazione/Societ� Trasparente";

	private static String templateFilename = "template_trasparenza.odt";

	public static void main(String[] args) throws Exception {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

		Segnalante segnalante = new Segnalante("Giancarlo", "Carbone", "CRBGCR68B20F839U", "RPCT");

		Organizzazione org = new Organizzazione("Comune di Bugliano", "Toscana", "Pisa", "Bugliano");
		org.setTipoEnte("Comune");
		org.setMail("prova@bugliano.gov.it");
		org.setPec("protocollo@bugliano.pec.it");
		org.setTelefono("+39 06060606");
		org.setCodiceFiscale("123456789012");
		segnalante.setEnte(org);

		SegnalazioneTrasparenza segnalazione = new SegnalazioneTrasparenza(segnalante, dateformat.parse("07-12-2021"), org,
				LINK_AMM_TRASP, CARENZA);

		Carenza ca1 = new Carenza("Disposizioni generali");
		ca1.addSezione("Piano triennale per la prevenzione della corruzione e della trasparenza");
		ca1.addSezione("Atti generali");
		ca1.setContenutoObbligo("Aenean volutpat lorem vel metus commodo condimentum congue eget urna.");
		
		segnalazione.addCarenza(ca1);
		
		Carenza ca2 = new Carenza("Opere pubbliche");
		ca2.addSezione("Atti di programmazione delle opere pubbliche");
		ca2.addSezione("Tempi costi e indicatori di realizzazione delle opere pubbliche");
		ca2.setContenutoObbligo("Aenean volutpat lorem vel metus commodo condimentum congue eget urna.");
		
		segnalazione.addCarenza(ca2);
	
		// Oggetto della segnalazione NOTA: IN QUESTO CASO OGGETTO SEGNALAZIONE NON utilizzato
		// segnalazione.setOggetto(OGGETTO_SEGNALAZIONE);

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
		segnalazione.addAllegato(new Allegato("lettera.pdf", "Lettera incarico", ""));

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

		InputStream in = new FileInputStream(new File("resource/template/" + templateFilename));

		OutputStream out = new FileOutputStream(
				"resource/pdf/test_" + TestTrasparenza.class.getSimpleName() + "_" + System.currentTimeMillis() + ".pdf");

		PdfBuilder.buildPdf(in, out, segnalazione);
		System.out.println("END");

	}
}
