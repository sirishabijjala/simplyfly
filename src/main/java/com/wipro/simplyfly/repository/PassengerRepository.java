package com.wipro.simplyfly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.simplyfly.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long>{

}
