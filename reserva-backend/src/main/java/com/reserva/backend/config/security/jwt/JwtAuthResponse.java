package com.reserva.backend.config.security.jwt;

public class JwtAuthResponse {
	
	private String username;
	private String role;
	private String accessToken;
	private String typeToken;
	
	public JwtAuthResponse(String username, String role, String accessToken, String typeToken) {
		super();
		this.username = username;
		this.role = role;
		this.accessToken = accessToken;
		this.typeToken = typeToken;
	}

	public JwtAuthResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
