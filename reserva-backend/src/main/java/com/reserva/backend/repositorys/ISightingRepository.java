package com.reserva.backend.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.backend.entities.Sighting;

public interface ISightingRepository extends JpaRepository<Sighting, Long>{

    @Query(value = "SELECT * FROM sighting s WHERE s.created_by_id =:id AND s.active =:active", nativeQuery = true)
    public List<Sighting> findByUserIdAndActive(@Param("id") long id, @Param("active") boolean active);

    @Query(value = "SELECT * FROM sighting s " +
    "INNER JOIN sighting_type st ON s.sighting_type_id = st.id " +
    "WHERE s.active =:active " +
    "AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(s.scientific_name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
    "AND (:status IS NULL OR s.status =:status) " +
    "AND (:type IS NULL OR st.name =:type)",
    nativeQuery = true)
    public Page<Sighting> findAll(@Param("name") String name, @Param("status") String status, @Param("type") String type, @Param("active") boolean active, Pageable pageable);
    
    @Query(value = "SELECT * FROM sighting s INNER JOIN field f on s.id = f.sighting_id",
    nativeQuery = true)
    public List<Sighting> findAllForMap();
    
}
