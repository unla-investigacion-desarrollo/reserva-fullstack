package com.reserva.backend.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reserva.backend.entities.Field;

public interface IFieldRepository extends JpaRepository<Field, Long>{
    
}
