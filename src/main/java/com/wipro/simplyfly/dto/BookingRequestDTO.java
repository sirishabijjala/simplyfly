package com.wipro.simplyfly.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
   @NotNull(message="ScheduleId cannot be null")
   private Long scheduleId;
   @NotEmpty(message = "Passenger list cannot be empty")
   @Valid   // Important to validate PassengerDTO
   private List<PassengerDTO> passengers;
   @NotBlank(message = "Payment method is required")
   private String paymentMethod;
   public BookingRequestDTO() {
	super();
   }
   public BookingRequestDTO(Long scheduleId, List<PassengerDTO> passengers,
		String paymentMethod) {
	super();
	this.scheduleId = scheduleId;
	this.passengers = passengers;
	this.paymentMethod = paymentMethod;
   }
   public Long getScheduleId() {
	return scheduleId;
   }
   public void setScheduleId(Long scheduleId) {
	this.scheduleId = scheduleId;
   }
  
   public List<PassengerDTO> getPassengers() {
	return passengers;
   }
   public void setPassengers(List<PassengerDTO> passengers) {
	this.passengers = passengers;
   }
   public String getPaymentMethod() {
	return paymentMethod;
   }
   public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
   }
   @Override
   public String toString() {
	return "BookingRequestDTO [scheduleId=" + scheduleId + ", passengers=" + passengers
			+ ", paymentMethod=" + paymentMethod + "]";
   }
   



}