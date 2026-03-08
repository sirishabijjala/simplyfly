package com.wipro.simplyfly.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.FlightSearchResponseDTO;
import com.wipro.simplyfly.entity.Flight;

public interface IFlightService  {

	public List<FlightSearchResponseDTO> searchFlights(String from, String to, LocalDate date);
	Flight addFlight(FlightDTO flightDTO);

	List<Map<String, Object>> getSeatsBySchedule(Long scheduleId);
}
