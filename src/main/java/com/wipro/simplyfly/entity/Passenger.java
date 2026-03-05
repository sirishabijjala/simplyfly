package com.wipro.simplyfly.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private int age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat; // Links the person to their specific 1A or 1B

	public Passenger() {
		super();
	}

	public Passenger(Long id, String name, int age, String gender, Booking booking, Seat seat) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.booking = booking;
		this.seat = seat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
	    return "Passenger [id=" + id +
	            ", name=" + name +
	            ", age=" + age +
	            ", gender=" + gender + "]";
	}
    
    
}
