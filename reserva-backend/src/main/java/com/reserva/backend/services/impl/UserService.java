package com.reserva.backend.services.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reserva.backend.constants.UserConstants;
import com.reserva.backend.dto.user.UserRequestDto;
import com.reserva.backend.dto.user.UserResponseDto;
import com.reserva.backend.dto.user.UserUpdateDto;
import com.reserva.backend.repositorys.IRoleRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.IUserService;
import com.reserva.backend.util.ResponsePageable;
import com.reserva.backend.util.Responses;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.entities.Role;
import com.reserva.backend.entities.User;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Responses<UserResponseDto> create(UserRequestDto request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new ReservaException(UserConstants.USERNAME_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ReservaException(UserConstants.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		try {
			User newUser = modelMapper.map(request, User.class);
			newUser.setActive(true);
			BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
			newUser.setPassword(passEncoder.encode(request.getPassword()));
			Optional<Role> newRole = roleRepository.findByName(request.getRole());
			if (!newRole.isPresent()) {
				throw new ReservaException(UserConstants.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
			newUser.setRole(newRole.get());
			userRepository.save(newUser);
			UserResponseDto response = modelMapper.map(newUser, UserResponseDto.class);
			return new Responses<>(true, UserConstants.USER_CREATED, response);
		} catch (MappingException e) {
			throw new ReservaException(UserConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public UserResponseDto getById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent() || !user.get().isActive()) {
			throw new ReservaException(UserConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		UserResponseDto response = modelMapper.map(user.get(), UserResponseDto.class);
		return response;
	}

	@Override
	public Responses<UserResponseDto> update(long id, UserUpdateDto request) {
		Optional<User> user = userRepository.findById(id);
		if (id != request.getId()) {// SI PATH ES DIFERENTE DE LO QUE SE MANDAN EN EL JSON
			throw new ReservaException(UserConstants.RESOURCE_ERROR_ID_MISMATCH, HttpStatus.UNAUTHORIZED);
		}
		if (!user.get().isActive()) {
			throw new ReservaException(UserConstants.USER_INACTIVE, HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByUsername(request.getUsername())
				&& !user.get().getUsername().equalsIgnoreCase(request.getUsername())) {
			throw new ReservaException(UserConstants.USERNAME_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(request.getEmail())
				&& !user.get().getEmail().equalsIgnoreCase(request.getEmail())) {
			throw new ReservaException(UserConstants.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		try {
			User updateUser = user.get();
			updateUser.setName(request.getName());
			updateUser.setUsername(request.getUsername());
			updateUser.setEmail(request.getEmail());
			updateUser.setActive(request.isActive());
			Optional<Role> updateRole = roleRepository.findByName(request.getRole());
			if (!updateRole.isPresent()) {
				throw new ReservaException(UserConstants.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
			updateUser.setRole(updateRole.get());
			userRepository.save(updateUser);
			return new Responses<>(true, UserConstants.USER_UPDATE_SUCCESSFUL, getById(id));
		} catch (ReservaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReservaException(UserConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public Responses<UserResponseDto> delete(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ReservaException(UserConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (!user.isActive()) {
			throw new ReservaException(UserConstants.USER_INACTIVE, HttpStatus.BAD_REQUEST);
		}
		try {
			userRepository.delete(user);
			return new Responses<>(true, UserConstants.USER_DELETE_SUCCESSFUL, null);
		} catch (Exception e) {
			throw new ReservaException(UserConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public Responses<UserResponseDto> restore(long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ReservaException(UserConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
		if (user.isActive()) {
			throw new ReservaException(UserConstants.USER_ACTIVE, HttpStatus.BAD_REQUEST);
		}
		try {
			user.setActive(true);
			userRepository.save(user);
			return new Responses<>(true, UserConstants.USER_RESTORE_SUCCESSFUL, getById(id));
		} catch (Exception e) {
			throw new ReservaException(UserConstants.REQUEST_FAILURE, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponsePageable<UserResponseDto> getAll(String name, int page, int size, String orderBy, String sortBy) {
		try {
			if(page < 1) page = 1; if (size < 1) size = 999999;
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(
					orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
			Page<User> pageUser = userRepository.findByNameContaining(name, pageable);
			return new ResponsePageable<>(page, pageUser.getTotalPages(),
					pageUser.getContent().stream()
							.map(user -> modelMapper.map(user, UserResponseDto.class))
							.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new ReservaException(UserConstants.USER_LIST_ERROR, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
