package com.wipro.simplyfly.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "simplyflydb")
public class Schedule {
	private LocalDateTime departure_time;
	private LocalDateTime arrival_time;
	private int total_seats;
	private int available_seats;
	public LocalDateTime getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(LocalDateTime departure_time) {
		this.departure_time = departure_time;
	}
	public LocalDateTime getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(LocalDateTime arrival_time) {
		this.arrival_time = arrival_time;
	}
	public int getTotal_seats() {
		return total_seats;
	}
	public void setTotal_seats(int total_seats) {
		this.total_seats = total_seats;
	}
	public int getAvailable_seats() {
		return available_seats;
	}
	public void setAvailable_seats(int available_seats) {
		this.available_seats = available_seats;
	}
	public Schedule(LocalDateTime departure_time, LocalDateTime arrival_time, int total_seats, int available_seats) {
		super();
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
		this.total_seats = total_seats;
		this.available_seats = available_seats;
	}
	public Schedule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
