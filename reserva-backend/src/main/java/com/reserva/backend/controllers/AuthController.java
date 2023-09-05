package com.reserva.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reserva.backend.constants.AuthConstants;
import com.reserva.backend.dto.auth.ForgotPasswordDto;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;
import com.reserva.backend.dto.auth.ResetPasswordDto;
import com.reserva.backend.exceptions.CustomResponse;
import com.reserva.backend.services.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private IAuthService authService;
	
	@Operation(summary = "realiza el inicio de sesion de un usuario previamente registrado en la base de datos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.SIGN_IN_SUSSCEFUL, content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.USERNAME_OR_PASSWORD_INCORRECT, content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto request){
		return ResponseEntity.ok(new CustomResponse<>(authService.signin(request), null));
	}
	
	@Operation(summary = "realiza el registro de un usuario nuevo, sin estar previamente registrado en la base de datos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.SIGN_UP_SUSSCEFUL, content = @Content),
			@ApiResponse(responseCode = "404", description = AuthConstants.ROLE_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.USERNAME_ALREADY_EXIST + " | "
					+ AuthConstants.EMAIL_ALREADY_EXIST, content = @Content) })
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDto request){
		return ResponseEntity.ok(new CustomResponse<>(authService.signup(request), null));
	}
	
	@Operation(summary = "realiza el envio de una solicitud de recuperacion de contraseña al email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.EMAIL_SEND_OK, content = @Content),
			@ApiResponse(responseCode = "404", description = AuthConstants.EMAIL_NOT_FOUND,  content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.EMAIL_SEND_ERROR, content = @Content)
	})
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto request){
		return ResponseEntity.ok(new CustomResponse<>(authService.ForgotPassword(request), null));
	}
	
	@Operation(summary = "realiza el cambio de contraseña mas la verificacion del token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.PASSWORD_HAS_BEEN_CHANGED, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.TOKEN_BAD_REQUEST, content = @Content),
			@ApiResponse(responseCode = "403", description = AuthConstants.TOKEN_INVALID,  content = @Content)
	})
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto request){
		return ResponseEntity.ok(new CustomResponse<>(authService.resetPassword(request), null));
	}

}