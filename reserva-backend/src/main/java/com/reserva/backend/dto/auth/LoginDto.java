package com.reserva.backend.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {
	
	@NotBlank(message = "usernameOrEmail no debe estar vacio")
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String usernameOrEmail;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;
	
	public LoginDto(String usernameOrEmail, String password) {
		super();
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	public LoginDto() {
		super();
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
