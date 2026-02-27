package com.wipro.simplyfly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.simplyfly.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
