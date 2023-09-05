package com.reserva.backend.dto.auth;

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
public class ResetPasswordDto {
	
	@NotBlank(message = "el token no debe estar vacio")
	private String token;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String passwordRepeat;

}
