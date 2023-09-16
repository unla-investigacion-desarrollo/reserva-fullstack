package com.reserva.backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.Sighting;

public interface ISightingRepository extends JpaRepository<Sighting, Long>{
    
}
