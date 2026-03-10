package com.wipro.simplyfly.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionRequestDTO {
	@NotNull(message="bookingid cannot be null")
	private long bookingId;
	@NotBlank(message="payment method cannot be blank")
	private String paymentMethod;
	public TransactionRequestDTO() {
		super();
	}
	public TransactionRequestDTO(long bookingId, String paymentMethod) {
		super();
		this.bookingId = bookingId;
		this.paymentMethod = paymentMethod;
	}

	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Override
	public String toString() {
		return "PaymentRequestDTO [bookingId=" + bookingId + ", paymentMethod=" + paymentMethod + "]";
	}


}
