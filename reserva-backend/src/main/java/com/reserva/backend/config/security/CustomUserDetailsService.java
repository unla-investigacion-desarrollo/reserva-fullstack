package com.reserva.backend.config.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reserva.backend.repositorys.IUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	public IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		com.reserva.backend.entities.User user = userRepository
				.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Usuario no encontrado con ese username o email : " + usernameOrEmail));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName())));

	}

}
