package com.wipro.simplyfly.dto;

public class BookingRequestDTO {
private Long userId;
private Long ScheduleId;
private int numberOfSeats;
private String paymentMethod;//CARD,UPI,NETBANKING
public BookingRequestDTO() {
	super();
}
public BookingRequestDTO(Long userId, Long scheduleId, int numberOfSeats) {
	super();
	this.userId = userId;
	ScheduleId = scheduleId;
	this.numberOfSeats = numberOfSeats;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public Long getScheduleId() {
	return ScheduleId;
}
public void setScheduleId(Long scheduleId) {
	ScheduleId = scheduleId;
}
public int getNumberOfSeats() {
	return numberOfSeats;
}
public void setNumberOfSeats(int numberOfSeats) {
	this.numberOfSeats = numberOfSeats;
}

public String getPaymentMethod() {
	return paymentMethod;
}
public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}
@Override
public String toString() {
	return "BookingRequestDTO [userId=" + userId + ", ScheduleId=" + ScheduleId + ", numberOfSeats=" + numberOfSeats
			+ "]";
}

	

}
