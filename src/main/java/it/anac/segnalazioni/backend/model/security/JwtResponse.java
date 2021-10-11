package it.anac.segnalazioni.backend.model.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 5691812415478885008L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}