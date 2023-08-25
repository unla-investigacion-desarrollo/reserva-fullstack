package com.reserva.backend.dto.user;

public class RoleDto {
	
	private long id;
	private String name;
	
	public RoleDto(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public RoleDto() {
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

}
