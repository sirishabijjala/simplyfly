package com.wipro.simplyfly.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.simplyfly.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByFlightId(Long flightId);

    List<Schedule> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
}