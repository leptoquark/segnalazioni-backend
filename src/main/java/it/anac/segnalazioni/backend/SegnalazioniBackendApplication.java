package it.anac.segnalazioni.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SegnalazioniBackendApplication
{
	  @GetMapping("/")
	  public String home() {
	    return "SEGNALAZIONI - BACKEND API <a href=\"/swagger-ui/\">SWAGGER</a>";
	  }
	
	public static void main(String[] args) {
		SpringApplication.run(SegnalazioniBackendApplication.class, args);
	}
}