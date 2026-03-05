package com.wipro.simplyfly.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Get schedules by Flight ID
    List<Schedule> findByFlightId(Long flightId);

    // Get schedules by departure time
    List<Schedule> findByDepartureTime(LocalDateTime departureTime);

    // Get schedules by Flight and Departure Time
    List<Schedule> findByFlightIdAndDepartureTime(Long flightId, LocalDateTime departureTime);

    // Get schedules by Route and Time Range
    List<Schedule> findByFlight_RouteAndDepartureTimeBetween(
            Route route,
            LocalDateTime start,
            LocalDateTime end
    );
}