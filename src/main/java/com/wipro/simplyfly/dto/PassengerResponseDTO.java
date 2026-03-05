package com.wipro.simplyfly.dto;

public class PassengerResponseDTO {
	private String name;
	private int age;
	private String gender;
	private String seatNumber;
	public PassengerResponseDTO() {
		super();
	}

	public PassengerResponseDTO(String name, int age, String gender, String seatNumber) {
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

	public String getSeatId() {
		return seatNumber;
	}

	public void setSeatId(String seatNumber) {
		this.seatNumber= seatNumber;
	}

	@Override
	public String toString() {
		return "PassengerDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", seatNumber=" + seatNumber + "]";
	}

	    

}
