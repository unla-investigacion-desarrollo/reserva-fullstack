package com.reserva.backend.config.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reserva.backend.entities.Role;
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
				mapRoles(user.getLstRoles()));

	}

	private Collection<? extends GrantedAuthority> mapRoles(Set<Role> roles) {
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
	}

}
