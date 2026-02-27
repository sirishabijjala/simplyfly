package com.wipro.simplyfly.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedules")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate departureDate;

	private LocalTime departureTime;

	private double fare;

	private int availableSeats;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id", nullable = false)
	private Flight flight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;	

	public Schedule() {
	}

	public Schedule(LocalDate departureDate, LocalTime departureTime, double fare, int availableSeats, Flight flight,
			Route route) {
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.fare = fare;
		this.availableSeats = availableSeats;
		this.flight = flight;
		this.route = route;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "Schedule{" + "id=" + id + ", departureDate=" + departureDate + ", departureTime=" + departureTime
				+ ", fare=" + fare + ", availableSeats=" + availableSeats + ", flightId="
				+ (flight != null ? flight.getId() : null) + ", routeId=" + (route != null ? route.getId() : null)
				+ '}';
	}
}