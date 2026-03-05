package com.wipro.simplyfly.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	private String role;

	private boolean active = true;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<FlightOwner> flightOwners;
	
	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<FlightOwner> getFlightOwners() {
		return flightOwners;
	}

	public void setFlightOwners(List<FlightOwner> flightOwners) {
		this.flightOwners = flightOwners;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", active=" + active + ", flightOwners=" + flightOwners + "]";
	}
	
	
}