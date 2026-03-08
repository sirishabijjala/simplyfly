package com.wipro.simplyfly.restcontroller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.FlightSearchResponseDTO;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.service.IFlightService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	@Autowired
	private IFlightService flightService;
	
	@PostMapping("/add-flight")
    public Flight addFlight(@RequestBody FlightDTO dto) {

        return flightService.addFlight(dto);
	}
	@GetMapping("/search")
	public ResponseEntity<List<FlightSearchResponseDTO>> searchFlights(@RequestParam String source,
			@RequestParam String destination,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		return ResponseEntity.ok(flightService.searchFlights(source, destination, date));
	}
	
	@GetMapping("/seats/{scheduleId}")
	public ResponseEntity<List<Map<String, Object>>> getSeatsForSchedule(@PathVariable Long scheduleId) {
	    return ResponseEntity.ok(flightService.getSeatsBySchedule(scheduleId));
	}
}
