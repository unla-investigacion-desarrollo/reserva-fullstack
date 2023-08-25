package com.reserva.backend.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.TipoAvistamiento;

public interface ITipoAvistamientoRepository extends JpaRepository<TipoAvistamiento, Long>{
	
	public boolean existsByName(String name);
	
	public Page<TipoAvistamiento> findByNameContaining(String name, Pageable pageable);
	
	public Page<TipoAvistamiento> findByCategory(String category, Pageable pageable);

}
