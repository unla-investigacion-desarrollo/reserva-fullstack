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
public class LoginDto {
	
	@NotBlank(message = "usernameOrEmail no debe estar vacio")
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String usernameOrEmail;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;
	
}
