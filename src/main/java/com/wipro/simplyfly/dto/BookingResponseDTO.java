package com.wipro.simplyfly.dto;

import java.time.LocalDateTime;

public class BookingResponseDTO {
	private Long bookingId;
	private String bookingReference;
	private String flightName;
	private String origin;
	private String destination;
	private int numberOfSeats;
	private double totalAmount;
	private String bookingStatus;
	private LocalDateTime bookingDate;
	public BookingResponseDTO() {
		super();
	}
	public BookingResponseDTO(Long bookingId, String bookingReference, String flightName, String origin,
			String destination, int numberOfSeats, double totalAmount, String bookingStatus,
			LocalDateTime bookingDate) {
		super();
		this.bookingId = bookingId;
		this.bookingReference = bookingReference;
		this.flightName = flightName;
		this.origin = origin;
		this.destination = destination;
		this.numberOfSeats = numberOfSeats;
		this.totalAmount = totalAmount;
		this.bookingStatus = bookingStatus;
		this.bookingDate = bookingDate;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}
	@Override
	public String toString() {
		return "BookingResponseDTO [bookingId=" + bookingId + ", bookingReference=" + bookingReference + ", flightName="
				+ flightName + ", origin=" + origin + ", destination=" + destination + ", numberOfSeats="
				+ numberOfSeats + ", totalAmount=" + totalAmount + ", bookingStatus=" + bookingStatus + ", bookingDate="
				+ bookingDate + "]";
	}
	
	

}
