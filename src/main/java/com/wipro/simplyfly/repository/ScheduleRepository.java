package com.wipro.simplyfly.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Get all schedules by Flight ID
    List<Schedule> findByFlightRouteId(Long flightId);


    // Get schedules by Flight and Date
    List<Schedule> findByFlightIdAndDepartureTime(Long flightId, LocalDate departureTime);

}