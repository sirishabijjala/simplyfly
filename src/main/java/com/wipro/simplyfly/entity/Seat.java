package com.wipro.simplyfly.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "seats",
uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "schedule_id"}))
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String seatNumber; // e.g., "1A", "12F"
	private String seatType; // "WINDOW", "AISLE", "MIDDLE"
	private boolean isAvailable = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private Schedule schedule;

	public Seat() {
		super();
	}

	public Seat(Long id, String seatNumber, String seatType, boolean isAvailable, Schedule schedule) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.seatType = seatType;
		this.isAvailable = isAvailable;
		this.schedule = schedule;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", seatNumber=" + seatNumber + ", seatType=" + seatType + ", isAvailable="
				+ isAvailable + ", schedule=" + schedule + "]";
	}

}
