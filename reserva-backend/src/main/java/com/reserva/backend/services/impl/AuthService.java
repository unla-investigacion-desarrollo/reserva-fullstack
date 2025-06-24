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
import com.reserva.backend.util.Response;
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
		JwtAuthResponse response = new JwtAuthResponse(user.getId(), user.getUsername(), user.getRole().getName(),
				token, "bearer");
		return Response.success(AuthConstants.SIGN_IN_SUCCESSFUL, response);
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
			return Response.success(AuthConstants.SIGN_UP_SUCCESSFUL, modelMapper.map(user, UserResponseDto.class));
		} catch (Exception e) {
			throw new ReservaException(AuthConstants.DATABASE_SAVE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Responses<String> forgotPassword(ForgotPasswordDto request) {
		try {
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

			String subject = "Reserva - Cambiar Contraseña";
			String link = "http://localhost:4200/account/reset-password?token=" + token.getToken(); 
			String body = buildEmailBody(link);

			try {
				emailInfoService.send(request.getEmail(), subject, body);
				return Response.success(AuthConstants.EMAIL_SEND_OK, null);
			} catch (MessagingException e) {
				throw new ReservaException(AuthConstants.EMAIL_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (ReservaException e) {
			throw e;
		} catch (Exception e) {
			throw new ReservaException("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String buildEmailBody(String link) {
		return """
				<!DOCTYPE html>
				<html>
				<head>
				    <style>
				        .button {
				            background-color: #4CAF50;
				            border: none;
				            color: white;
				            padding: 15px 32px;
				            text-align: center;
				            text-decoration: none;
				            display: inline-block;
				            font-size: 16px;
				            margin: 20px 0;
				            cursor: pointer;
				            border-radius: 4px;
				        }
				        .container {
				            font-family: Arial, sans-serif;
				            max-width: 600px;
				            margin: 0 auto;
				            padding: 20px;
				            border: 1px solid #ddd;
				            border-radius: 5px;
				        }
				    </style>
				</head>
				<body>
				    <div class="container">
				        <h2>Recuperación de Contraseña</h2>
				        <p>Hemos recibido una solicitud para restablecer tu contraseña.</p>
				        <p>Por favor, haz clic en el siguiente botón para continuar:</p>
				        <a href="%s" class="button">Restablecer Contraseña</a>
				        <p>Si no solicitaste este cambio, puedes ignorar este mensaje.</p>
				        <p><strong>El enlace expirará en 10 minutos.</strong></p>
				        <p>Saludos,<br>El equipo de Reserva</p>
				    </div>
				</body>
				</html>
				""".formatted(link);
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
			return Response.success(AuthConstants.PASSWORD_HAS_BEEN_CHANGED, null);
		} catch (Exception e) {
			throw new ReservaException(AuthConstants.DATABASE_SAVE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean validateUser(LoginDto request, User user) {
		return passEncoder.matches(request.getPassword(), user.getPassword());
	}

}
