package com.reserva.backend.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.backend.entities.Sighting;

public interface ISightingRepository extends JpaRepository<Sighting, Long>{

    public Page<Sighting> findByStatus(String status, Pageable pageable);

    @Query(value = "SELECT * FROM sighting s WHERE s.created_by_id =:id AND s.active =TRUE", nativeQuery = true)
    public List<Sighting> findByUserId(@Param("id") long id);
    
    @Query(value = "SELECT * FROM sighting s INNER JOIN sighting_type t ON s.sighting_type_id = t.id WHERE t.name =:type AND s.active =TRUE", nativeQuery = true)
    public Page<Sighting> findByType(@Param("type") String type, Pageable pageable);

    public Page<Sighting> findByActive(boolean active, Pageable pageable);
    
}
