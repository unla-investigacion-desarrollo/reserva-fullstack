package com.reserva.backend.dto;

public class TipoAvistamientoResponseDto {

	private String name;
	private String category;
	
	public TipoAvistamientoResponseDto(String name, String category) {
		super();
		this.name = name;
		this.category = category;
	}

	public TipoAvistamientoResponseDto() {
		super();
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
	
}
