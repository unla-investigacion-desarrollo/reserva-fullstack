package com.reserva.backend.services;

import java.util.List;

import com.reserva.backend.dto.user.UserRequestDto;
import com.reserva.backend.dto.user.UserResponseDto;
import com.reserva.backend.dto.user.UserUpdateDto;
import com.reserva.backend.util.Responses;

public interface IUserService {
	
	public Responses<UserResponseDto> create(UserRequestDto request);
	public UserResponseDto getById(long id);
	public Responses<UserResponseDto> update(long id, UserUpdateDto request);
	public Responses<UserResponseDto> delete(long id);
	public Responses<UserResponseDto> restore(long id);
	public List<UserResponseDto> getAll(String name, int page, int size, String orderBy, String sortBy);

}
