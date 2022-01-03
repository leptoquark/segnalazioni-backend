package it.anac.segnalazioni.backend.engine;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSenderHelper {
	
    private Logger logger = LoggerFactory.getLogger(MailSenderHelper.class);
	
	@Autowired
    private JavaMailSender emailSender;
	
	public void sendMessage(String to,
							String subject,
							String text,
							String name,
							String path) throws MessagingException
    { 	
    	MimeMessage message = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	helper.setTo(to);
    	helper.setSubject(subject);
    	helper.setText(text);
    	FileSystemResource file = new FileSystemResource(new File(path));
    	helper.addAttachment(name, file);
    	emailSender.send(message);
    }
	
	public void sendMessageBackground(String to,
			String subject,
			String text,
			String name,
			String path)
	{
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	try {
					sendMessage(to,subject,text,name,path);
				} catch (MessagingException e) {
					logger.error("Invio fallito per "+to,e);
				}
            }
        });
        emailExecutor.shutdown(); 
	}
}