package com.reserva.backend.dto.user;

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
public class UserUpdateDto {
	
	private long id;
	@NotBlank(message = "el nombre no debe estar vacio")
	@Size(max = 250, message = "el nombre no debe tener más de {max} caracteres")
	private String name;
	@NotBlank(message = "el username no debe estar vacio")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El username solo puede contener letras y numeros")
	@Size(max = 250, message = "el username no debe tener más de {max} caracteres")
	private String username;
	@NotBlank(message = "el email no debe estar vacio")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "El email no es valido")
	@Size(max = 250, message = "el email no debe tener más de {max} caracteres")
	private String email;
	private boolean active;
	@NotBlank(message = "no se puede asignar un rol vacio")
	@Pattern(regexp = "ROLE_USER|ROLE_PERSONAL_RESERVA", message = "Solo existen los roles 'ROLE_USER' y 'ROLE_PERSONAL_RESERVA'")
	private String role;

}
