package com.reserva.backend.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDto {
	
	@NotBlank(message = "email no debe estar vacio")
	@Email
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String email;

}
