package com.reserva.backend.services;

import com.reserva.backend.config.security.jwt.JwtAuthResponse;
import com.reserva.backend.dto.auth.ForgotPasswordDto;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;
import com.reserva.backend.dto.auth.ResetPasswordDto;

public interface IAuthService {
	
	public JwtAuthResponse signin(LoginDto request);
	public String signup(RegisterDto request);
	public String forgotPassword(ForgotPasswordDto request);
	public String resetPassword(ResetPasswordDto request);
	
}
