package com.reserva.backend.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPasswordDto {
	
	@NotBlank(message = "email no debe estar vacio")
	@Email
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String email;

	public ForgotPasswordDto(String email) {
		super();
		this.email = email;
	}

	public ForgotPasswordDto() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
