package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
import com.wipro.simplyfly.service.FlightOwnerService;

@RestController
@RequestMapping("/owner")
@PreAuthorize("hasAuthority('OWNER')")
public class FlightOwnerController {

    @Autowired
    private FlightOwnerService flightOwnerService;

    //FLIGHT

    @GetMapping("/flights/{ownerId}")
    public List<FlightDTO> getFlightsByOwner(@PathVariable Long ownerId) {
        return flightOwnerService.getFlightsByOwner(ownerId);
    }

    @PostMapping("/flights/{ownerId}")
    public FlightDTO addFlight(@PathVariable Long ownerId,
                               @RequestBody FlightDTO flightDTO) {
        return flightOwnerService.addFlight(ownerId, flightDTO);
    }

    @PutMapping("/flights/update/{flightId}")
    public FlightDTO updateFlight(@PathVariable Long flightId,
                                  @RequestBody FlightDTO flightDTO) {
        return flightOwnerService.updateFlight(flightId, flightDTO);
    }

    @DeleteMapping("/flights/delete/{flightId}")
    public void deleteFlight(@PathVariable Long flightId) {
        flightOwnerService.deleteFlight(flightId);
    }

    //SCHEDULE

    @GetMapping("/schedules/{flightId}")
    public List<ScheduleDTO> getSchedulesByFlight(@PathVariable Long flightId) {
        return flightOwnerService.getSchedulesByFlight(flightId);
    }

    @PostMapping("/schedules/{flightId}")
    public ScheduleDTO addSchedule(@PathVariable Long flightId,
                                   @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.addSchedule(flightId, scheduleDTO);
    }

    @PutMapping("/schedules/update/{scheduleId}")
    public ScheduleDTO updateSchedule(@PathVariable Long scheduleId,
                                      @RequestBody ScheduleDTO scheduleDTO) {
        return flightOwnerService.updateSchedule(scheduleId, scheduleDTO);
    }

    @DeleteMapping("/schedules/delete/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        flightOwnerService.deleteSchedule(scheduleId);
    }

    //BOOKING

    @GetMapping("/bookings/{ownerId}")
    public List<BookingResponseDTO> getBookingsByOwner(@PathVariable Long ownerId) {
        return flightOwnerService.getBookingsByOwner(ownerId);
    }

    @PutMapping("/bookings/refund/{bookingId}")
    public void refundBooking(@PathVariable Long bookingId) {
        flightOwnerService.refundBooking(bookingId);
    }
}