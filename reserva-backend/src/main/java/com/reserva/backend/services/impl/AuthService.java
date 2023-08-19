package com.reserva.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.reserva.backend.config.security.jwt.JwtAuthResponse;
import com.reserva.backend.config.security.jwt.JwtTokenProvider;
import com.reserva.backend.constants.AuthConstants;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;
import com.reserva.backend.entities.Role;
import com.reserva.backend.entities.User;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.IRoleRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.IAuthService;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public JwtAuthResponse signin(LoginDto request) {
		User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
				.orElseThrow(() -> {
					if (isEmail(request.getUsernameOrEmail())) {
						return new ReservaException(AuthConstants.EMAIL_NOT_EXIST, HttpStatus.NOT_FOUND);
					} else {
						return new ReservaException(AuthConstants.USERNAME_NOT_EXIST, HttpStatus.NOT_FOUND);
					}
				});

		if (!validateUser(request, user)) {
			throw new ReservaException(AuthConstants.USERNAME_OR_PASSWORD_INCORRECT, HttpStatus.UNAUTHORIZED);
		}

		String token = jwtTokenProvider.generateToken(user);

		JwtAuthResponse response = new JwtAuthResponse(user.getUsername(), user.getRole().getName(), token, "bearer");

		return response;
	}

	@Override
	public String signup(RegisterDto request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new ReservaException(AuthConstants.USERNAME_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ReservaException(AuthConstants.EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setActive(true);
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		user.setPassword(passEncoder.encode(request.getPassword()));

		Optional<Role> verificar = roleRepository.findByName("ROLE_USER");
		if (!verificar.isPresent()) {
			throw new ReservaException(AuthConstants.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		Role role = verificar.get();
		user.setRole(role);
		userRepository.save(user);

		return AuthConstants.SIGN_UP_SUSSCEFUL;
	}

	public boolean isEmail(String email) {
		return email.contains("@") && email != null;
	}

	public boolean validateUser(LoginDto request, User user) {
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		return passEncoder.matches(request.getPassword(), user.getPassword());
	}

}
