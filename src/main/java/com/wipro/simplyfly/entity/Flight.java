package com.wipro.simplyfly.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_number", unique = true, nullable = false)
    private String flightNumber;

    @Column(name = "flight_name", nullable = false)
    private String flightName;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private FlightOwner flightowner;

    @Column(name = "check_in_baggage")
    private String checkInBaggage;

    @Column(name = "cabin_baggage")
    private String cabinBaggage;

    public Flight() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public FlightOwner getOwner() {
		return flightowner;
	}

	public void setOwner(FlightOwner owner) {
		this.flightowner = owner;
	}

	public String getCheckInBaggage() {
		return checkInBaggage;
	}

	public void setCheckInBaggage(String checkInBaggage) {
		this.checkInBaggage = checkInBaggage;
	}

	public String getCabinBaggage() {
		return cabinBaggage;
	}

	public void setCabinBaggage(String cabinBaggage) {
		this.cabinBaggage = cabinBaggage;
	}

    
}