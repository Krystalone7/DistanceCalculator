package com.artyom.repository;

import com.artyom.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistanceRepository extends JpaRepository<Distance, Long> {

    Optional<Distance> findDistanceByFromCityNameAndToCityName(String fromCityName, String toCityName);
}
