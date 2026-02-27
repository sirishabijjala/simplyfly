package com.wipro.simplyfly.dto;

public class FlightOwnerDTO {

	private Long id;

	private String name;
	private String email;

	public FlightOwnerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FlightOwnerDTO(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
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

	@Override
	public String toString() {
		return "FlightOwnerDTO [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

}
