package com.reserva.backend.services;

import com.reserva.backend.config.security.jwt.JwtAuthResponse;
import com.reserva.backend.dto.auth.ForgotPasswordDto;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;
import com.reserva.backend.dto.auth.ResetPasswordDto;
import com.reserva.backend.dto.user.UserResponseDto;
import com.reserva.backend.util.Responses;

public interface IAuthService {
	
	public Responses<JwtAuthResponse> signin(LoginDto request);
	public Responses<UserResponseDto> signup(RegisterDto request);
	public Responses<String> forgotPassword(ForgotPasswordDto request);
	public Responses<String> resetPassword(ResetPasswordDto request);
	
}
