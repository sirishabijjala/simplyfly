package com.wipro.simplyfly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Get all schedules by Flight ID
    List<Schedule> findByFlightId(Long flightId);

    // Get all schedules by Route ID
    List<Schedule> findByRouteId(int routeId);

    // Get schedules by departure date
    List<Schedule> findByDepartureDate(LocalDate departureDate);

    // Get schedules by Flight and Date
    List<Schedule> findByFlightIdAndDepartureDate(Long flightId, LocalDate departureDate);

}