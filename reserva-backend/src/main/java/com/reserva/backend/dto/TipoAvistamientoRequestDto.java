package com.reserva.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TipoAvistamientoRequestDto {
	
	private long id;
	@NotBlank(message = "el nombre no debe estar vacio")
	@Size(max = 250, message = "el nombre no debe tener más de {max} caracteres")
	private String name;
	@NotBlank(message = "no se puede una categoria vacia")
	@Pattern(regexp = "Flora|Fauna", message = "Solo existen dos categorias 'Flora' y 'Fauna'")
	private String category;
	private boolean active;
	
	public TipoAvistamientoRequestDto(long id, String name, String category, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.active = active;
	}

	public TipoAvistamientoRequestDto() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
