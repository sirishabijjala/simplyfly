package com.wipro.simplyfly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Get bookings by owner
    List<Booking> findBookingsByOwnerId(Long ownerId);

    // Check if bookings exist for a schedule
    boolean existsByScheduleId(Long scheduleId);

}