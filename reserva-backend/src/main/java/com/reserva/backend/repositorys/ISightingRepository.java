package com.reserva.backend.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.backend.entities.Sighting;

public interface ISightingRepository extends JpaRepository<Sighting, Long>{

    @Query(value = "SELECT * FROM sighting s WHERE s.created_by_id =:id", nativeQuery = true)
    public List<Sighting> findByUserId(@Param("id") long id);
    
}
