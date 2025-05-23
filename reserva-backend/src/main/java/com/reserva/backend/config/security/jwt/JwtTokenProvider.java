package com.reserva.backend.config.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.reserva.backend.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return token;
	}

	public String generateToken(User user) {

		String username = user.getUsername();
		String role = user.getRole().getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

		String token = Jwts.builder().claim("role", role).setSubject(username).setIssuedAt(currentDate)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return token;
	}

	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.error("Firma JWT no valida", HttpStatus.BAD_REQUEST);
		} catch (MalformedJwtException ex) {
			log.error("Token JWT no valida", HttpStatus.BAD_REQUEST);
		} catch (ExpiredJwtException ex) {
			log.error("Token JWT caducado", HttpStatus.BAD_REQUEST);
		} catch (UnsupportedJwtException ex) {
			log.error("Token JWT no compatible", HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException ex) {
			log.error("La cadena claims JWT esta vacia", HttpStatus.BAD_REQUEST);
		}
		return false;
	}

}
