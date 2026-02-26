package com.wipro.simplyfly.dto;

public class TransactionRequestDTO {
	private long bookingId;
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
