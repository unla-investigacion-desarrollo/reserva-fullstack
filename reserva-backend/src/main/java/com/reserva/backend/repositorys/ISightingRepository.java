package com.reserva.backend.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.backend.entities.Sighting;

public interface ISightingRepository extends JpaRepository<Sighting, Long>{

    public Page<Sighting> findByNameContainingOrScientificNameContainingAndActive(String name, String scientificName, boolean active, Pageable pageable);

    public Page<Sighting> findByStatusAndActive(String status, boolean active, Pageable pageable);

    @Query(value = "SELECT * FROM sighting s WHERE s.created_by_id =:id AND s.active =:active", nativeQuery = true)
    public List<Sighting> findByUserIdAndActive(@Param("id") long id, @Param("active") boolean active);
    
    @Query(value = "SELECT * FROM sighting s INNER JOIN sighting_type t ON s.sighting_type_id = t.id WHERE t.name =:type AND s.active =:active", nativeQuery = true)
    public Page<Sighting> findByTypeAndActive(@Param("type") String type, @Param("active") boolean active, Pageable pageable);

    public Page<Sighting> findByActive(boolean active, Pageable pageable);
    
}
