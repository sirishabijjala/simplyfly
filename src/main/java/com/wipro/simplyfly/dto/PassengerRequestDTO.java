package com.wipro.simplyfly.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PassengerRequestDTO {
	@NotBlank(message="Passenger name cannot be null")
	private String name;
	@Min(value = 0, message="Age must be greater than or equal to 0")
	@Max(value = 120, message="Age seems invalid")
	private int age;
	@NotBlank(message="Gender is required")
	private String gender;
	@NotBlank(message="SeatNumber cannot be null")
	private String seatNumber;

	public PassengerRequestDTO() {
		super();
	}

	public PassengerRequestDTO(String name, int age, String gender, String seatNumber) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.seatNumber = seatNumber;
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

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "PassengerDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", seatNumber=" + seatNumber + "]";
	}

}
