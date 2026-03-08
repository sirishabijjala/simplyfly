package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.service.FlightOwnerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasAuthority('OWNER')")
public class FlightOwnerController {

    @Autowired
    private FlightOwnerService flightOwnerService;

    // --- OWNER PROFILE ---
    
    @GetMapping("/profile")
    public FlightOwner getMyProfile() {
        Long ownerId = flightOwnerService.getCurrentOwnerId();
        return flightOwnerService.getOwnerById(ownerId);
    }
    @GetMapping("/flights")
    public List<FlightDTO> getOwnerFlights(){

        Long ownerId = flightOwnerService.getCurrentOwnerId();

        return flightOwnerService.getFlightsByOwner(ownerId);
    }
   
    // --- FLIGHT MANAGEMENT ---

    @GetMapping("/my-flights")
    public List<FlightDTO> getFlightsByOwner() {
        Long ownerId = flightOwnerService.getCurrentOwnerId();
        return flightOwnerService.getFlightsByOwner(ownerId);
    }

    @PostMapping("/flights/{routeId}")
    public FlightDTO addFlight(@PathVariable int routeId, @RequestBody FlightDTO flightDTO) {
        Long ownerId = flightOwnerService.getCurrentOwnerId();
        return flightOwnerService.addFlight(routeId, ownerId, flightDTO);
    }

    @PutMapping("/flights/{flightId}")
    public FlightDTO updateFlight(@PathVariable Long flightId, @RequestBody FlightDTO flightDTO) {
        return flightOwnerService.updateFlight(flightId, flightDTO);
    }

    @DeleteMapping("/flights/{flightId}")
    public void deleteFlight(@PathVariable Long flightId) {
        flightOwnerService.deleteFlight(flightId);
    }

    // --- SCHEDULE MANAGEMENT ---

    @GetMapping("/schedules/{flightId}")
    public List<ScheduleDTO> getSchedulesByFlight(@PathVariable Long flightId) {
        return flightOwnerService.getSchedulesByFlight(flightId);
    }

    @PostMapping("/schedules/{flightId}")
    public ScheduleDTO addSchedule(@PathVariable Long flightId, @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.addSchedule(flightId, scheduleDTO);
    }

    @PutMapping("/schedules/{scheduleId}")
    public ScheduleDTO updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.updateSchedule(scheduleId, scheduleDTO);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        flightOwnerService.deleteSchedule(scheduleId);
    }

    // --- BOOKING & REFUNDS ---

    @GetMapping("/my-bookings")
    public List<BookingResponseDTO> getBookingsByOwner() {
        Long ownerId = flightOwnerService.getCurrentOwnerId();
        return flightOwnerService.getBookingsByOwner(ownerId);
    }

    @PutMapping("/bookings/{bookingId}/refund")
    public void refundBooking(@PathVariable Long bookingId) {
        flightOwnerService.refundBooking(bookingId);
    }

    // --- ROUTE MANAGEMENT ---

    @PostMapping("/addRoute")
    public RouteDTO addRoute(@RequestBody RouteDTO routeDTO) {
        return flightOwnerService.addRoute(routeDTO);
    }
}