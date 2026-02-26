package com.wipro.simplyfly.dto;



public class FlightDTO {
	private Long id;
	
	private String flightNumber;
	
	private String flightName;
	
	private FlightOwnerDTO flightownerdto;
	
	 private String checkInBaggage;
	 
	 private String cabinBaggage;

	 public FlightDTO() {
		super();
		// TODO Auto-generated constructor stub
	 }

	 public FlightDTO(Long id, String flightNumber, String flightName, FlightOwnerDTO flightownerdto,
			String checkInBaggage, String cabinBaggage) {
		super();
		this.id = id;
		this.flightNumber = flightNumber;
		this.flightName = flightName;
		this.flightownerdto = flightownerdto;
		this.checkInBaggage = checkInBaggage;
		this.cabinBaggage = cabinBaggage;
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

	 public FlightOwnerDTO getFlightownerdto() {
		 return flightownerdto;
	 }

	 public void setFlightownerdto(FlightOwnerDTO flightownerdto) {
		 this.flightownerdto = flightownerdto;
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

	 @Override
	 public String toString() {
		return "FlightDTO [id=" + id + ", flightNumber=" + flightNumber + ", flightName=" + flightName
				+ ", flightownerdto=" + flightownerdto + ", checkInBaggage=" + checkInBaggage + ", cabinBaggage="
				+ cabinBaggage + "]";
	 }
	 
	 

}
