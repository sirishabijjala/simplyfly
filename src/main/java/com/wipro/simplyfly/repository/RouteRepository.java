package com.wipro.simplyfly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Route;


@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	Optional<Route> findBySourceAndDestination(String source, String destination);
	boolean existsBySourceAndDestination(String source, String destination);

}
