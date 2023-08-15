package com.reserva.backend.services;

import com.reserva.backend.config.security.jwt.JwtAuthResponse;
import com.reserva.backend.dto.auth.LoginDto;
import com.reserva.backend.dto.auth.RegisterDto;

public interface IAuthService {
	
	public JwtAuthResponse signin(LoginDto request);

	public String signup(RegisterDto request);
	
}
