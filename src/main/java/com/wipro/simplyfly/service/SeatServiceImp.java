package com.wipro.simplyfly.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.entity.Seat;
import com.wipro.simplyfly.repository.SeatRepository;
@Service
public class SeatServiceImp implements ISeatService{
	@Autowired
	SeatRepository seatRepository;
	

	@Override
	public void createSeatsForSchedule(Schedule schedule) {

	    List<Seat> seats = new ArrayList<>();

	    int rows = 30;
	    String[] seatLetters = {"A", "B", "C", "D"};

	    for(int row = 1; row <= rows; row++) {

	        for(String letter : seatLetters) {

	            Seat seat = new Seat();

	            seat.setSeatNumber(row + letter);

	            if(letter.equals("A") || letter.equals("D"))
	                seat.setSeatType("WINDOW");
	            else if(letter.equals("B") || letter.equals("C"))
	                seat.setSeatType("AISLE");

	            seat.setAvailable(true);
	            seat.setSchedule(schedule);

	            seats.add(seat);
	        }
	    }

	    seatRepository.saveAll(seats);
	}

}
