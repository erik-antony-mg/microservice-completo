package com.microservices.repository;

import com.microservices.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {

    Optional<Region> findByName(String name);
}
