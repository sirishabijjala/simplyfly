package com.wipro.simplyfly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.simplyfly.entity.Seat;
import com.wipro.simplyfly.entity.User;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat,Long>{
	List<Seat> findByScheduleId(Long scheduleId);
	List<Seat> findByScheduleIdAndIsAvailableTrue(Long scheduleId);
	Optional<Seat> findByIdAndScheduleId(Long seatId, Long scheduleId);
	Optional<Seat> findBySeatNumber(String seatNumber);
	Optional<Seat> findBySeatNumberAndScheduleId(String seatNumber,Long scheduleId);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Seat s WHERE s.seatNumber = :seatNumber AND s.schedule.id = :scheduleId")
	Optional<Seat> findSeatForUpdate(
	        @Param("seatNumber") String seatNumber,
	        @Param("scheduleId") Long scheduleId
	);

}
