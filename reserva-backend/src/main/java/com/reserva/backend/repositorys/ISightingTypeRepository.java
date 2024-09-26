package com.reserva.backend.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reserva.backend.entities.SightingType;

public interface ISightingTypeRepository extends JpaRepository<SightingType, Long>{
	
	public SightingType findByName(String name);

	public boolean existsByName(String name);

	@Query(value = "SELECT * FROM sighting_type st " +
    "WHERE st.active =:active " +
    "AND (:name IS NULL OR LOWER(st.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
    "AND (:category IS NULL OR LOWER(st.category) = LOWER(:category))", nativeQuery = true)
	public Page<SightingType> findAll(@Param("name") String name, @Param("category") String category, @Param("active") boolean active, Pageable pageable);

	@Query(value = "select * from sighting_type where id = (:id)" , nativeQuery = true)
	public SightingType findById(@Param("id") long id);
}
