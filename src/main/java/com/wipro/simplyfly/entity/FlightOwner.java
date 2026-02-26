package com.wipro.simplyfly.entity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "flightowner")
public class FlightOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    // One Owner → Many Flights
    @OneToMany(mappedBy = "flightOwner", cascade = CascadeType.ALL)
    private List<Flight> flights;

    public FlightOwner() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

   
}