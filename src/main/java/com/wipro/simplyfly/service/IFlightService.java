package com.wipro.simplyfly.service;

import java.time.LocalDate;
import java.util.List;

import com.wipro.simplyfly.dto.FlightSearchResponseDTO;

public interface IFlightService  {

	public List<FlightSearchResponseDTO> searchFlights(String from, String to, LocalDate date);
}
