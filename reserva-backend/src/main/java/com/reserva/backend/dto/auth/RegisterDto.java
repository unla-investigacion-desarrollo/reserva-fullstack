package com.reserva.backend.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	
	@NotBlank(message = "el nombre no debe estar vacio")
	@Size(max = 250, message = "el nombre no debe tener más de {max} caracteres")
	private String name;
	@NotBlank(message = "el username no debe estar vacio")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El username solo puede contener letras y numeros")
	@Size(max = 250, message = "el username no debe tener más de {max} caracteres")
	private String username;
	@NotBlank(message = "el email no debe estar vacio")
	@Email
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String email;
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;
	
}
