package com.wipro.simplyfly.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	// Get all schedules by Flight ID
	List<Schedule> findByFlightRouteId(Long flightId);

<<<<<<< HEAD
    // Get schedules by departure date
    List<Schedule> findByDepartureTime(LocalDate departureTime);

    // Get schedules by Flight and Date
    List<Schedule> findByFlightIdAndDepartureTime(Long flightId, LocalDate departureTime);
=======
	// Get schedules by Flight and Date
	List<Schedule> findByFlightIdAndDepartureTime(Long flightId, LocalDate departureTime);

	List<Schedule> findByFlight_RouteAndDepartureTimeBetween(Route route, LocalDateTime start, LocalDateTime end);
>>>>>>> 7fe4232195589fa4ee1b1d297802f8e1ec93f68a

}
