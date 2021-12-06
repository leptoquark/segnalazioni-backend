package it.anac.segnalazioni.backend.model.mailer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import it.avcp.spc.amp.engine.ejb.livelloUno.GestioneAlertBean;

public class Mailer {
	 public Mailer() throws IOException, NamingException
	 {
		 Context ctx;
		Properties props = new Properties();
		InputStream input = new FileInputStream("amp.properties");
		props.load(input);
        ctx = new InitialContext(props);
		GestioneAlertBean alertBean = (GestioneAlertBean) ctx.lookup("AVCP-AMPCoreServicesEAR/GestioneAlertBean/remote");
	 }
}
