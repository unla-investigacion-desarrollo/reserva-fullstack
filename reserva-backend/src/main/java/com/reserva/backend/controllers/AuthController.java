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
import com.reserva.backend.services.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/account")
public class AuthController {
	
	@Autowired
	private IAuthService authService;
	
	@Operation(summary = "Inicio de sesión")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.SIGN_IN_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.PASSWORD_MISMATCH, content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto request){
		return ResponseEntity.ok(authService.signin(request));
	}
	
	@Operation(summary = "Registro de un usuario nuevo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.SIGN_UP_SUCCESS, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.USERNAME_TAKEN + " | "
					+ AuthConstants.EMAIL_TAKEN, content = @Content),
			@ApiResponse(responseCode = "404", description = AuthConstants.ROLE_NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.DATABASE_SAVE_FAILED, content = @Content) })
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDto request){
		return ResponseEntity.ok(authService.signup(request));
	}
	
	@Operation(summary = "Envío de token de recuperación de contraseña")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.EMAIL_SENT_SUCCESSFULLY, content = @Content),
			@ApiResponse(responseCode = "404", description = AuthConstants.EMAIL_NOT_FOUND,  content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.EMAIL_SEND_FAILED, content = @Content)
	})
	@PostMapping("/recovery")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto request){
		return ResponseEntity.ok(authService.forgotPassword(request));
	}
	
	@Operation(summary = "Cambio de contraseña con verificación de token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = AuthConstants.PASSWORD_CHANGED, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.TOKEN_REQUEST_INVALID, content = @Content),
			@ApiResponse(responseCode = "400", description = AuthConstants.PASSWORD_MISMATCH, content = @Content),
			@ApiResponse(responseCode = "403", description = AuthConstants.INVALID_TOKEN,  content = @Content),
			@ApiResponse(responseCode = "500", description = AuthConstants.DATABASE_SAVE_FAILED, content = @Content)
	})
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto request){
		return ResponseEntity.ok(authService.resetPassword(request));
	}

}