package com.reserva.backend.services.impl;

import java.util.Date;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.reserva.backend.config.security.jwt.JwtAuthResponse;
import com.reserva.backend.config.security.jwt.JwtTokenProvider;
import com.reserva.backend.constants.AuthConstants;
import com.reserva.backend.dto.auth.ForgotPasswordDto;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;
import com.reserva.backend.dto.auth.ResetPasswordDto;
import com.reserva.backend.dto.user.UserResponseDto;
import com.reserva.backend.entities.Role;
import com.reserva.backend.entities.TokenVerification;
import com.reserva.backend.entities.User;
import com.reserva.backend.exceptions.ReservaException;
import com.reserva.backend.repositorys.IRoleRepository;
import com.reserva.backend.repositorys.ITokenVerificationRepository;
import com.reserva.backend.repositorys.IUserRepository;
import com.reserva.backend.services.IAuthService;
import com.reserva.backend.services.IEmailInfoService;
import com.reserva.backend.util.Responses;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private ITokenVerificationRepository tokenVerificationRepository;
	
	@Autowired
	private IEmailInfoService emailInfoService;
	
	private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Responses<JwtAuthResponse> signin(LoginDto request) {
		User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
				.orElseThrow(() -> {
					throw new ReservaException(AuthConstants.USERNAME_OR_PASSWORD_INCORRECT, HttpStatus.UNAUTHORIZED);
				});
		if (!validateUser(request, user)) {
			throw new ReservaException(AuthConstants.USERNAME_OR_PASSWORD_INCORRECT, HttpStatus.UNAUTHORIZED);
		}
		String token = jwtTokenProvider.generateToken(user);
		JwtAuthResponse response = new JwtAuthResponse(user.getId(), user.getUsername(), user.getRole().getName(), token, "bearer");
		return new Responses<>(true, AuthConstants.SIGN_IN_SUCCESSFUL, response);
	}

	@Override
	public Responses<UserResponseDto> signup(RegisterDto request) {
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
		user.setPassword(passEncoder.encode(request.getPassword()));
		Role role = roleRepository.findByName(AuthConstants.USER)
				.orElseThrow(() -> new ReservaException(AuthConstants.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));
		user.setRole(role);
		try {
			userRepository.save(user);
			return new Responses<>(true, AuthConstants.SIGN_UP_SUCCESSFUL,
					modelMapper.map(user, UserResponseDto.class));
		} catch (Exception e) {
			throw new ReservaException(AuthConstants.DATABASE_SAVE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Responses<String> forgotPassword(ForgotPasswordDto request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ReservaException(AuthConstants.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
		TokenVerification token = tokenVerificationRepository.findByUser(user);
		if (token == null) {
			token = new TokenVerification(user);
		} else {
			token.setToken(token.generateToken());
			token.setCreatedAt(new Date());
			token.setExpiratedAt(token.getEpirationTime(10));
		}
		tokenVerificationRepository.save(token);
		String subjet = "Reserva - Cambiar Contraseña";
		String link = "http://localhost:8000/api/auth/reset-password?token=" + token.getToken();
		String body = "Hola, se solicito un cambio de contraseña:" + "<br><a href='" + link + "'>" + link + "</a>"
				+ "<br><br>Recorda que el token es valido solamente por 10 minutos"
				+ "<br><br><br>Saludos,<br>Reserva.";
		try {
			emailInfoService.send(request.getEmail(), subjet, body);
			return new Responses<>(true, AuthConstants.EMAIL_SEND_OK, null);
		} catch (MessagingException e) {
			throw new ReservaException(AuthConstants.EMAIL_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@Override
	public Responses<String> resetPassword(ResetPasswordDto request) {
		TokenVerification token = tokenVerificationRepository.findByToken(request.getToken());
		if (token == null) {
			throw new ReservaException(AuthConstants.TOKEN_BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}
		Date expiration = token.getExpiratedAt();
		Date now = new Date();
		if (request.getToken() == null || now.after(expiration)) {
			throw new ReservaException(AuthConstants.TOKEN_INVALID, HttpStatus.FORBIDDEN);
		}
		User user = token.getUser();
		if (!request.getPassword().equals(request.getPasswordRepeat())) {
			throw new ReservaException(AuthConstants.PASSWORD_NOT_MATCH, HttpStatus.BAD_REQUEST);
		}
		user.setPassword(passEncoder.encode(request.getPassword()));
		try {
			tokenVerificationRepository.save(token);
			return new Responses<>(true, AuthConstants.PASSWORD_HAS_BEEN_CHANGED, null);
		} catch (Exception e) {
			throw new ReservaException(AuthConstants.DATABASE_SAVE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean validateUser(LoginDto request, User user) {
		return passEncoder.matches(request.getPassword(), user.getPassword());
	}

}
