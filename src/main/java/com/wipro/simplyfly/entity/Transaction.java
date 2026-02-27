package com.wipro.simplyfly.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String transactionId;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="booking_id",nullable=false)
	private Booking booking;
	private double amount;
	private String paymentMethod;
	//CARD,UPI,NETBANKING
	private String paymentStatus;
	//SUCCESS,FAILED,REFUNDED
	private LocalDateTime transactionDate;
	public Transaction() {
		super();
	}
	public Transaction(Long id, String transactionId, Booking booking, double amount, String paymentMethod,
			String paymentStatus, LocalDateTime transactionDate) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.booking = booking;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionDate = transactionDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
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
		return "Transaction [id=" + id + ", transactionId=" + transactionId + ", booking=" + booking + ", amount="
				+ amount + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus
				+ ", transactionDate=" + transactionDate + "]";
	}
	
	
	

}
