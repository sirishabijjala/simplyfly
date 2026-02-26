package com.wipro.simplyfly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Route {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    private Double distance;

    private String estimatedDuration;

	public Route() {
		super();
	}

	public Route(int id, String source, String destination, Double distance, String estimatedDuration) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
		this.estimatedDuration = estimatedDuration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getEstimatedDuration() {
		return estimatedDuration;
	}

	public void setEstimatedDuration(String estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", source=" + source + ", destination=" + destination + ", distance=" + distance
				+ ", estimatedDuration=" + estimatedDuration + "]";
	}
    
    
}
