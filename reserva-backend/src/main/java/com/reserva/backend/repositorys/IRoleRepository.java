package com.reserva.backend.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{
	
	public Optional<Role> findByName(String name);

}