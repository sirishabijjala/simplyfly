package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import com.wipro.simplyfly.dto.*;
import com.wipro.simplyfly.entity.FlightOwner;
=======
import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
>>>>>>> 7fe4232195589fa4ee1b1d297802f8e1ec93f68a
import com.wipro.simplyfly.service.FlightOwnerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/owner")
@PreAuthorize("hasRole('OWNER')")
public class FlightOwnerController {

    @Autowired
    private FlightOwnerService flightOwnerService;

    // OWNER
    @GetMapping("/{ownerId}")
    public FlightOwner getOwner(@PathVariable Long ownerId) {
        return flightOwnerService.getOwnerById(ownerId);
    }

    // FLIGHT
    @GetMapping("/flights/{ownerId}")
    public List<FlightDTO> getFlights(@PathVariable Long ownerId) {
        return flightOwnerService.getFlightsByOwner(ownerId);
    }

    @PostMapping("/flights/{ownerId}")
    public FlightDTO addFlight(@PathVariable Long ownerId,
                               @RequestBody FlightDTO flightDTO) {
        return flightOwnerService.addFlight(ownerId, flightDTO);
    }

    @PutMapping("/flights/{flightId}")
    public FlightDTO updateFlight(@PathVariable Long flightId,
                                  @RequestBody FlightDTO flightDTO) {
        return flightOwnerService.updateFlight(flightId, flightDTO);
    }

    @DeleteMapping("/flights/{flightId}")
    public void deleteFlight(@PathVariable Long flightId) {
        flightOwnerService.deleteFlight(flightId);
    }

    // SCHEDULE
    @GetMapping("/schedules/{flightId}")
    public List<ScheduleDTO> getSchedules(@PathVariable Long flightId) {
        return flightOwnerService.getSchedulesByFlight(flightId);
    }

    @PostMapping("/schedules/{flightId}")
    public ScheduleDTO addSchedule(@PathVariable Long flightId,
                                   @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.addSchedule(flightId, scheduleDTO);
    }

    @PutMapping("/schedules/{scheduleId}")
    public ScheduleDTO updateSchedule(@PathVariable Long scheduleId,
                                      @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.updateSchedule(scheduleId, scheduleDTO);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        flightOwnerService.deleteSchedule(scheduleId);
    }

    // BOOKING
    @GetMapping("/bookings/{ownerId}")
    public List<BookingResponseDTO> getBookings(@PathVariable Long ownerId) {
        return flightOwnerService.getBookingsByOwner(ownerId);
    }

<<<<<<< HEAD
    @PutMapping("/bookings/refund/{bookingId}")
    public void refundBooking(@PathVariable Long bookingId) {
        flightOwnerService.refundBooking(bookingId);
=======
	@PutMapping("/schedules/{scheduleId}")
	public ScheduleDTO updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
		return flightOwnerService.updateSchedule(scheduleId, scheduleDTO);
	}

	@DeleteMapping("/schedules/{scheduleId}")
	public void deleteSchedule(@PathVariable Long scheduleId) {
		flightOwnerService.deleteSchedule(scheduleId);
	}

	// BOOKING

	@GetMapping("/{ownerId}/bookings")
	public List<BookingResponseDTO> getBookingsByOwner(@PathVariable Long ownerId) {
		return flightOwnerService.getBookingsByOwner(ownerId);
	}

	@PutMapping("/bookings/{bookingId}/refund")
	public void refundBooking(@PathVariable Long bookingId) {
		flightOwnerService.refundBooking(bookingId);
	}
	
	@PostMapping("/addRoute")
    public RouteDTO addRoute(@RequestBody RouteDTO routeDTO) {

        RouteDTO savedRoute = flightOwnerService.addRoute(routeDTO);
        return savedRoute;
>>>>>>> 7fe4232195589fa4ee1b1d297802f8e1ec93f68a
    }
}