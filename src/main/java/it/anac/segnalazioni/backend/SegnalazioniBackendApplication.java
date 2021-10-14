package it.anac.segnalazioni.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SegnalazioniBackendApplication
{
	@Autowired
	private Environment environment;
    
	private String getActiveProfiles()
	{
		String out = "Profilo:";
	        for (String profileName : environment.getActiveProfiles()) {
	            out = out +" " + profileName;
	        }  
	        
	     return out;
	}
	
	@Value("${segnalazione.versione}")
    private String versione;
	private String getVersion()
	{
		return versione;
	}
	
	@GetMapping("/")
	public String landingPage()
	{
	    return " SEGNALAZIONI - BACKEND API <a href=\"/swagger-ui/\">SWAGGER</a><br>versione "+getVersion()+"@"+getActiveProfiles();
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(SegnalazioniBackendApplication.class, args);
	}
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}