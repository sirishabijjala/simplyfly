package com.wipro.simplyfly.dto;

public class PassengerDTO {
	private String name;
	private int age;
	private String gender;
	private Long seatId;

	public PassengerDTO() {
		super();
	}

	public PassengerDTO(String name, int age, String gender, Long seatId) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.seatId = seatId;
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

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	@Override
	public String toString() {
		return "PassengerDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", seatId=" + seatId + "]";
	}

}
