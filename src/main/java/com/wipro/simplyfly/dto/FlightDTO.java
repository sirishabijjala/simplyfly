package com.wipro.simplyfly.dto;



public class FlightDTO {
	private Long id;

	private String flightNumber;

	private String flightName;

	private Long flightOwnerId;

	 private String checkInBaggage;

	 private String cabinBaggage;
	 private int routeId;

	 public FlightDTO() {
		super();
	 }

	 public FlightDTO(Long id, String flightNumber, String flightName, Long flightownerId,
			String checkInBaggage, String cabinBaggage) {
		super();
		this.id = id;
		this.flightNumber = flightNumber;
		this.flightName = flightName;
		this.flightOwnerId = flightownerId;
		this.checkInBaggage = checkInBaggage;
		this.cabinBaggage = cabinBaggage;
	 }
	 

	 public FlightDTO(Long id, String flightNumber, String flightName, Long flightOwnerId, String checkInBaggage,
			String cabinBaggage, int routeId) {
		super();
		this.id = id;
		this.flightNumber = flightNumber;
		this.flightName = flightName;
		this.flightOwnerId = flightOwnerId;
		this.checkInBaggage = checkInBaggage;
		this.cabinBaggage = cabinBaggage;
		this.routeId = routeId;
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

	 public Long getFlightOwnerIdo() {
		 return flightOwnerId;
	 }

	 public void setFlightownerdto(Long flightOwnerId) {
		 this.flightOwnerId= flightOwnerId;
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

	 public int getRouteId() {
		return routeId;
	}

	 public void setRouteId(int routeId) {
		 this.routeId = routeId;
	 }

	 @Override
	 public String toString() {
		return "FlightDTO [id=" + id + ", flightNumber=" + flightNumber + ", flightName=" + flightName
				+ ", flightOwnerId=" + flightOwnerId + ", checkInBaggage=" + checkInBaggage + ", cabinBaggage="
				+ cabinBaggage + "]";
	 }



}
