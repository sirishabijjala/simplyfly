package com.wipro.simplyfly.dto;

import java.time.LocalDateTime;

public class FlightSearchResponseDTO {

	private Long scheduleId;
	private String flightName;
	private String flightNumber;
	private String source;
	private String destination;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private double fare;
	private int availableSeats;

	public FlightSearchResponseDTO() {
		super();
	}

	public FlightSearchResponseDTO(Long scheduleId, String flightName, String flightNumber, String source,
			String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, double fare,
			int availableSeats) {
		super();
		this.scheduleId = scheduleId;
		this.flightName = flightName;
		this.flightNumber = flightNumber;
		this.source = source;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.fare = fare;
		this.availableSeats = availableSeats;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
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

	@Override
	public String toString() {
		return "FlightSearchResponseDTO [scheduleId=" + scheduleId + ", flightName=" + flightName + ", flightNumber="
				+ flightNumber + ", source=" + source + ", destination=" + destination + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + ", fare=" + fare + ", availableSeats="
				+ availableSeats + "]";
	}

}
