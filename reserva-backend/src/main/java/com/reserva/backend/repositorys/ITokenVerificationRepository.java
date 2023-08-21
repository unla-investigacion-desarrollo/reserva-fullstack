package com.reserva.backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.TokenVerification;
import com.reserva.backend.entities.User;

public interface ITokenVerificationRepository extends JpaRepository<TokenVerification, Long>{
	
	TokenVerification findByToken(String token);
	
	TokenVerification findByUser(User user);

}
