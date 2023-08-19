package com.reserva.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDto {
	
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
	@NotBlank(message = "la contraseña no debe estar vacia")
	@Size(max = 250, message = "la contraseña no debe tener más de {max} caracteres")
	private String password;
	private boolean active;
	@NotBlank(message = "no se puede asignar un rol vacio")
	@Pattern(regexp = "ROLE_USER|ROLE_PERSONAL_RESERVA", message = "Solo existen los roles 'ROLE_USER' y 'ROLE_PERSONAL_RESERVA'")
	private String role;
	
	public UserRequestDto(String name, String username, String email, String password, boolean active, String role, long id) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.active = active;
		this.role = role;
		this.id = id;
	}

	public UserRequestDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
