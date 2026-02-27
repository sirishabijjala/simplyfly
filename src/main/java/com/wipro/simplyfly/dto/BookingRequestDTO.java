package com.wipro.simplyfly.dto;

public class BookingRequestDTO {
	private Long id;
	private int numberOfSeats;
	public BookingRequestDTO() {
		super();
	}
	public BookingRequestDTO(Long id, int numberOfSeats) {
		super();
		this.id = id;
		this.numberOfSeats = numberOfSeats;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	@Override
	public String toString() {
		return "BookingRequestDTO [id=" + id + ", numberOfSeats=" + numberOfSeats + "]";
	}
	

}
