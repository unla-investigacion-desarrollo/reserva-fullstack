package com.reserva.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	
	private long id;
	private String name;
	private String username;
	private String email;
	private boolean active;
	private RoleDto role;
	
}
