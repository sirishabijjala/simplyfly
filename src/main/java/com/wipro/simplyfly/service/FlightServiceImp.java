package com.wipro.simplyfly.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.FlightSearchResponseDTO;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.exceptions.FlightNotFoundException;
import com.wipro.simplyfly.exceptions.RouteNotFoundException;
import com.wipro.simplyfly.repository.RouteRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;

@Service
public class FlightServiceImp implements IFlightService {

	@Autowired
	RouteRepository routeRepo;

	@Autowired
	ScheduleRepository scheduleRepo;

	@Override
	public List<FlightSearchResponseDTO> searchFlights(String source, String destination, LocalDate date) {
		Route route = routeRepo.findBySourceAndDestination(source, destination)
				.orElseThrow(() -> new RouteNotFoundException(source, destination));

		List<Schedule> schedules = scheduleRepo.findByFlight_RouteAndDepartureTimeBetween(route, date.atStartOfDay(),
				date.atTime(LocalTime.MAX));
		
		if (schedules.isEmpty()) {
	        throw new FlightNotFoundException(source,destination,date);
	    }

		return schedules.stream().map(s -> {
			FlightSearchResponseDTO dto = new FlightSearchResponseDTO();
			dto.setScheduleId(s.getId());
			dto.setFlightName(s.getFlight().getFlightName());
			dto.setFlightNumber(s.getFlight().getFlightNumber());
			dto.setSource(source);
			dto.setDestination(destination);
			dto.setDepartureTime(s.getDepartureTime());
			dto.setArrivalTime(s.getArrivalTime());
			dto.setFare(s.getFare());
			dto.setAvailableSeats(s.getAvailableSeats());
			return dto;
		}).collect(Collectors.toList());
	}
}
