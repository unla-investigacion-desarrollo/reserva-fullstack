package com.reserva.backend.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordDto {
	
	@NotBlank(message = "el token no debe estar vacio")
	private String token;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;

	
	public ResetPasswordDto(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}

	public ResetPasswordDto() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
