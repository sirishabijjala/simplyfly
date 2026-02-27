package com.wipro.simplyfly.dto;

import java.time.LocalDateTime;

public class TransactionResponseDTO {
	private String transactionId;
	private Long bookingId;
	private double amount;
	private String paymentMethod;
	private String paymentStatus;
	private LocalDateTime transactionDate;
	public TransactionResponseDTO() {
		super();
	}
	public TransactionResponseDTO(String transactionId, Long bookingId, double amount, String paymentMethod,
			String paymentStatus, LocalDateTime transactionDate) {
		super();
		this.transactionId = transactionId;
		this.bookingId = bookingId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionDate = transactionDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	@Override
	public String toString() {
		return "PaymentResponseDTO [transactionId=" + transactionId + ", bookingId=" + bookingId + ", amount=" + amount
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + ", transactionDate="
				+ transactionDate + "]";
	}
	

}
