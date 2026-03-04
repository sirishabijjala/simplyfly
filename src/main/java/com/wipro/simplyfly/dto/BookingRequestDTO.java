package com.wipro.simplyfly.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
@NotNull(message="UserID cannot be null")
private Long userId;
@NotNull(message="ScheduleId cannot be null")
private Long scheduleId;
@Min(value=1,message="Atleast one seat has to select for booking")
private int numberOfSeats;
@NotNull(message="Payment method is required")
private String paymentMethod;//CARD,UPI,NETBANKING
public BookingRequestDTO() {
	super();
}
public BookingRequestDTO(Long userId, Long scheduleId, int numberOfSeats) {
	super();
	this.userId = userId;
	this.scheduleId = scheduleId;
	this.numberOfSeats = numberOfSeats;
}


public BookingRequestDTO(@NotNull(message = "UserID cannot be null") Long userId,
		@NotNull(message = "ScheduleId cannot be null") Long scheduleId,
		@Min(value = 1, message = "Atleast one seat has to select for booking") int numberOfSeats,
		@NotNull(message = "Payment method is required") String paymentMethod) {
	super();
	this.userId = userId;
	this.scheduleId = scheduleId;
	this.numberOfSeats = numberOfSeats;
	this.paymentMethod = paymentMethod;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public Long getScheduleId() {
	return scheduleId;
}
public void setScheduleId(Long scheduleId) {
	 this.scheduleId = scheduleId;
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
	return "BookingRequestDTO [userId=" + userId + ", ScheduleId=" + scheduleId + ", numberOfSeats=" + numberOfSeats
			+ "]";
}

	

}