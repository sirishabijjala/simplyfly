package com.wipro.simplyfly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.Route;

@Repository

public interface FlightRepository extends JpaRepository<Flight, Long> {

	
	boolean existsByRoute(Route route);


    // Get flights by owner
    List<Flight> findByFlightOwnerId(Long ownerId);

}