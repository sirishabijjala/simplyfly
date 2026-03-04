package com.wipro.simplyfly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.simplyfly.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat,Long>{
	List<Seat> findByScheduleId(Long scheduleId);
	List<Seat> findByScheduleIdAndAvailableTrue(Long scheduleId);
	Optional<Seat> findByIdAndScheduleId(Long seatId, Long scheduleId);

}
