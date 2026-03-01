package com.wipro.simplyfly.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private FlightOwner flightOwner;

    @Column(name = "check_in_baggage")
    private String checkInBaggage;

    @Column(name = "cabin_baggage")
    private String cabinBaggage;

    @ManyToOne
    @JoinColumn(name="route_id", nullable=false)
    private Route route;

    // ✅ Relationship Fixed
    @OneToMany(mappedBy="flight", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Schedule> schedules;

    public Flight() {
    }

    public Flight(Long id, String flightNumber, String flightName,
                  FlightOwner flightOwner, Route route,
                  String checkInBaggage, String cabinBaggage) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.flightName = flightName;
        this.flightOwner = flightOwner;
        this.route = route;
        this.checkInBaggage = checkInBaggage;
        this.cabinBaggage = cabinBaggage;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getFlightName() { return flightName; }
    public void setFlightName(String flightName) { this.flightName = flightName; }

    public FlightOwner getFlightOwner() { return flightOwner; }
    public void setFlightOwner(FlightOwner flightOwner) { this.flightOwner = flightOwner; }

    public String getCheckInBaggage() { return checkInBaggage; }
    public void setCheckInBaggage(String checkInBaggage) { this.checkInBaggage = checkInBaggage; }

    public String getCabinBaggage() { return cabinBaggage; }
    public void setCabinBaggage(String cabinBaggage) { this.cabinBaggage = cabinBaggage; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public List<Schedule> getSchedules() { return schedules; }
    public void setSchedules(List<Schedule> schedules) { this.schedules = schedules; }
}