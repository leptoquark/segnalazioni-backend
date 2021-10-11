package it.anac.segnalazioni.backend.rest.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${rest.password}")
    private String password;
	
	@Value("${rest.username}")
    private String username;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("segnalazioni".equals(username)) {
			
			return new User(username, bCryptPasswordEncoder.encode(password),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}