package com.wipro.simplyfly.dto;

import java.util.List;



public class FlightOwnerDTO {
	
	private Long id;
	
    private String name;
    private String email;
    
    private List<FlightDTO> flightdto;
    

	public FlightOwnerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public FlightOwnerDTO(Long id, String name, String email, List<FlightDTO> flightdto) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.flightdto = flightdto;
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


	public List<FlightDTO> getFlightdto() {
		return flightdto;
	}


	public void setFlightdto(List<FlightDTO> flightdto) {
		this.flightdto = flightdto;
	}


	@Override
	public String toString() {
		return "FlightOwnerDTO [id=" + id + ", name=" + name + ", email=" + email + ", flightdto=" + flightdto + "]";
	}
    
    

}
