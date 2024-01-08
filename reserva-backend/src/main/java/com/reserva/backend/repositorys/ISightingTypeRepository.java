package com.reserva.backend.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.SightingType;

public interface ISightingTypeRepository extends JpaRepository<SightingType, Long>{
	
	public SightingType findByName(String name);

	public boolean existsByName(String name);
	
	public Page<SightingType> findByNameContainingAndActive(String name, boolean active, Pageable pageable);
	
	public Page<SightingType> findByCategoryAndActive(String category, boolean active, Pageable pageable);

	public Page<SightingType> findByActive(boolean active, Pageable pageable);

}
