package com.wipro.simplyfly.dto;

public class SeatDTO {
    private Long id;
    private String seatNumber; // 1A, 1B, etc.
    private String seatType;   // WINDOW, AISLE
    private boolean isAvailable;
	public SeatDTO() {
		super();
	}
	public SeatDTO(Long id, String seatNumber, String seatType, boolean isAvailable) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.seatType = seatType;
		this.isAvailable = isAvailable;
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
	@Override
	public String toString() {
		return "SeatDTO [id=" + id + ", seatNumber=" + seatNumber + ", seatType=" + seatType + ", isAvailable="
				+ isAvailable + "]";
	}
    
    
}
