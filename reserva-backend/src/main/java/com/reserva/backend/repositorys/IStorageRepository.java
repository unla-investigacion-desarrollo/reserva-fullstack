package com.reserva.backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.Image;

public interface IStorageRepository extends JpaRepository<Image, Long>{
    
}
