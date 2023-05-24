package com.kn.koshelap.disney.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kn.koshelap.disney.domain.Component;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long>, JpaSpecificationExecutor<Component> {
    Optional<Component> findByName(String name);

    @Query(value = "SELECT * FROM component c WHERE  c.site_id = :id", nativeQuery = true)
    List<Component> findActiveComponentBySiteId(Long id);
}
