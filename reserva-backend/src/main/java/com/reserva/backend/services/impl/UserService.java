package com.reserva.backend.services.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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

import com.reserva.backend.dto.UserRequestDto;
import com.reserva.backend.dto.UserResponseDto;
import com.reserva.backend.dto.UserUpdateDto;
import com.reserva.backend.repositorys.IRoleRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.IUserService;
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
	public UserResponseDto create(UserRequestDto request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new ReservaException("el username ya existe", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new ReservaException("el email ya existe", HttpStatus.BAD_REQUEST);
		}
		try {
			User newUser = modelMapper.map(request, User.class);
			newUser.setActive(true);
			BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
			newUser.setPassword(passEncoder.encode(request.getPassword()));
			Optional<Role> newRole = roleRepository.findByName(request.getName());
			if(!newRole.isPresent()) {
				throw new ReservaException("no existe un rol con ese nombre", HttpStatus.NOT_FOUND);
			}
			newUser.setRole(newRole.get());
			userRepository.save(newUser);
			UserResponseDto response = modelMapper.map(newUser, UserResponseDto.class);
			return response;
		}catch(MappingException e) {
			throw new ReservaException("algo salió mal en el mapeo", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public UserResponseDto getById(long id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent() || !user.get().isActive()) {
			throw new ReservaException("no se encontró ningun usuario con ese id", HttpStatus.NOT_FOUND);
		}
		UserResponseDto response = modelMapper.map(user.get(), UserResponseDto.class);
		return response;
	}

	@Override
	public String update(long id, UserUpdateDto request) {
		Optional<User> user = userRepository.findById(id);
		if(id != request.getId()) {//SI PATH ES DIFERENTE DE LO QUE SE MANDAN EN EL JSON
			throw new ReservaException("los id's no coinciden, no puedes utilizar a este recurso", HttpStatus.UNAUTHORIZED);
		}
		if(!user.get().isActive()) {
			throw new ReservaException("el usuario esta inactivo", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByUsername(request.getUsername()) && !user.get().getUsername().equalsIgnoreCase(request.getUsername())) {
			throw new ReservaException("el username ya se encuentra en uso", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(request.getEmail()) && !user.get().getEmail().equalsIgnoreCase(request.getEmail())) {
			throw new ReservaException("el email ya se encuentra en uso", HttpStatus.BAD_REQUEST);
		}
		User updateUser = user.get();
		updateUser.setName(request.getName());
		updateUser.setUsername(request.getUsername());
		updateUser.setEmail(request.getEmail());
		updateUser.setActive(request.isActive());
		Optional<Role> updateRole = roleRepository.findByName(request.getRole());
		if (!updateRole.isPresent()) {
			throw new ReservaException("No existe un rol con ese nombre", HttpStatus.NOT_FOUND);
		}
		updateUser.setRole(updateRole.get());
		userRepository.save(updateUser);
		return "usuario actualizado correctamente";
	}

	@Override
	public String delete(long id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new ReservaException("usuario no encotrado", HttpStatus.NOT_FOUND);
		}
		if(!user.get().isActive()) {
			throw new ReservaException("el usuario ya se encuentra dado de baja", HttpStatus.BAD_REQUEST);
		}
		user.get().setActive(false);
		userRepository.save(user.get());
		return "usuario dado de baja correctamente";
	}

	@Override
	public String restore(long id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new ReservaException("usuario no encotrado", HttpStatus.NOT_FOUND);
		}
		if(user.get().isActive()) {
			throw new ReservaException("el usuario ya se encuentra dado de alta", HttpStatus.BAD_REQUEST);
		}
		user.get().setActive(true);
		userRepository.save(user.get());
		return "usuario dado de alta correctamente";
	}

	@Override
	public List<UserResponseDto> getAll(String name, int page, int size, String orderBy, String sortBy) {
		try {
			if(page < 1) page = 1; if (size < 1) size = 999999;
			Pageable pageable = PageRequest.of(page - 1, size, Sort.by(orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy.toLowerCase()));
			Page<User> pageUser = userRepository.findByNameContaining(name, pageable);
			List<UserResponseDto> response = new ArrayList<>();
			for(User u : pageUser.getContent()) {
				response.add(modelMapper.map(u, UserResponseDto.class));
			}
			return response;
		}catch(Exception e) {
			throw new ReservaException("no se pudieron listar los usuarios", HttpStatus.EXPECTATION_FAILED);
		}
	}

}
