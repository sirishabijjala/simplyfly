package com.wipro.simplyfly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.FlightOwner;

@Repository
public interface FlightOwnerRepository extends JpaRepository<FlightOwner, Long>{
	
	Optional<FlightOwner> findByAccountEmail(String email);

}
