package com.kn.koshelap.disney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.kn.koshelap.disney.domain.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, JpaSpecificationExecutor<Site> {
    Optional<Site> findByName(String name);
}
