package com.wipro.simplyfly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.simplyfly.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.schedule.flight.flightOwner.id = :ownerId")
	List<Booking> findBookingsByOwnerId(@Param("ownerId") Long ownerId);

	boolean existsBySchedule_Flight_FlightOwner_Id(Long ownerId);
}