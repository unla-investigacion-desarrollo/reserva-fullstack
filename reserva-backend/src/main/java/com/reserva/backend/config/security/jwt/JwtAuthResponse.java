package com.reserva.backend.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthResponse {
	
	private long id;
	private String username;
	private String role;
	private String accessToken;
	private String typeToken;

}
