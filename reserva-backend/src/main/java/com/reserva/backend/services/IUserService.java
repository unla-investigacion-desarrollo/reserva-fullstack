package com.reserva.backend.services;

import java.util.List;

import com.reserva.backend.dto.UserRequestDto;
import com.reserva.backend.dto.UserResponseDto;
import com.reserva.backend.dto.UserUpdateDto;

public interface IUserService {
	
	public UserResponseDto create(UserRequestDto request);
	public UserResponseDto getById(long id);
	public String update(long id, UserUpdateDto request);
	public String delete(long id);
	public String restore(long id);
	public List<UserResponseDto> getAll();

}
