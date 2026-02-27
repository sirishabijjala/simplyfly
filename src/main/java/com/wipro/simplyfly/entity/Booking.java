package com.wipro.simplyfly.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; //pk
	@Column(unique = true)
	private String bookingReference;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;//fk
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="schedule_id",nullable=false)
	private Schedule schedule; //fk
	private int numberOfSeats;
	private double totalAmount;
	private String bookingStatus;
	//PENDING,CONFIRMED,CANCELLED
	private LocalDateTime bookingDate;
	public Booking() {
		super();
	}
	public Booking(Long id, String bookingReference, User user, Schedule schedule, int numberOfSeats,
			double totalAmount, String bookingStatus, LocalDateTime bookingDate) {
		super();
		this.id = id;
		this.bookingReference = bookingReference;
		this.user = user;
		this.schedule = schedule;
		this.numberOfSeats = numberOfSeats;
		this.totalAmount = totalAmount;
		this.bookingStatus = bookingStatus;
		this.bookingDate = bookingDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
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
		return "Booking [id=" + id + ", bookingReference=" + bookingReference + ", user=" + user + ", schedule="
				+ schedule + ", numberOfSeats=" + numberOfSeats + ", totalAmount=" + totalAmount + ", bookingStatus="
				+ bookingStatus + ", bookingDate=" + bookingDate + "]";
	}
	
	
}
