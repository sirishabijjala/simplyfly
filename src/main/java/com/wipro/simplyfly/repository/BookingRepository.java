package com.wipro.simplyfly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.andrewoma.dexx.collection.List;
import com.wipro.simplyfly.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	@Query("SELECT b FROM Booking b WHERE b.flight.flightOwner.id = :ownerId")
	List<Booking> findBookingsByOwnerId(@Param("ownerId") Long ownerId);
}
