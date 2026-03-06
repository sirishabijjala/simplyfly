package com.wipro.simplyfly.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Get schedules by Flight ID
    List<Schedule> findByFlightId(Long flightId);

    // Check if schedules exist for a flight
    boolean existsByFlightId(Long flightId);

    // Get schedules by departure time
    List<Schedule> findByDepartureTime(LocalDateTime departureTime);

    // Get schedules by Flight and Departure Time
    List<Schedule> findByFlightIdAndDepartureTime(Long flightId, LocalDateTime departureTime);

    // Search schedules by route and time range
    List<Schedule> findByFlight_RouteAndDepartureTimeBetween(
            Route route,
            LocalDateTime start,
            LocalDateTime end);
    
    @Query("SELECT s FROM Schedule s WHERE s.flight.route = :route " +
    	       "AND s.departureTime >= :start AND s.departureTime <= :end")
    	List<Schedule> findSchedulesByRouteAndDate(
    	    @Param("route") Route route, 
    	    @Param("start") LocalDateTime start, 
    	    @Param("end") LocalDateTime end
    	);
}