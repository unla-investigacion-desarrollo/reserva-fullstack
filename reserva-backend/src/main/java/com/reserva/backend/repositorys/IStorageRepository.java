package com.reserva.backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.Image;

public interface IStorageRepository extends JpaRepository<Image, Long>{
    List<Image> findBySightingId(Long sightingId); // Spring Data JPA creará la implementación automáticamente
    
}
