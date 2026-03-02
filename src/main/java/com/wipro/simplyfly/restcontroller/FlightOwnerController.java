package com.wipro.simplyfly.restcontroller;
	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.*;

	import com.wipro.simplyfly.dto.BookingResponseDTO;
	import com.wipro.simplyfly.dto.FlightDTO;
	import com.wipro.simplyfly.dto.ScheduleDTO;
	import com.wipro.simplyfly.service.FlightOwnerService;

	@RestController
	@RequestMapping("/api/owner")
	@CrossOrigin(origins = "*")
	public class FlightOwnerController {

	    @Autowired
	    private FlightOwnerService flightOwnerService;

	   
	    //FLIGHT
	    

//	    @GetMapping("/{ownerId}/flights")
////	    public List<FlightDTO> getFlightsByOwner(@PathVariable Long ownerId) {
////	        return flightOwnerService.getFlightsByOwner(ownerId);
//	    }

	    @PostMapping("/{ownerId}/flights")
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

	    
	    //SCHEDULE
	    

	    @GetMapping("/flights/{flightId}/schedules")
	    public List<ScheduleDTO> getSchedulesByFlight(@PathVariable Long flightId) {
	        return flightOwnerService.getSchedulesByFlight(flightId);
	    }

	    @PostMapping("/flights/{flightId}/schedules")
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


	    @GetMapping("/{ownerId}/bookings")
	    public List<BookingResponseDTO> getBookingsByOwner(@PathVariable Long ownerId) {
	        return flightOwnerService.getBookingsByOwner(ownerId);
	    }

	    @PutMapping("/bookings/{bookingId}/refund")
	    public void refundBooking(@PathVariable Long bookingId) {
	        flightOwnerService.refundBooking(bookingId);
	    }
	}

import java.util.List;
import com.wipro.simplyfly.service.FlightOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;


@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('OWNER')")   
public class FlightOwnerController {

    @Autowired
    private FlightOwnerService flightOwnerService;

    //FLIGHT

    @GetMapping("/{ownerId}/flights")
    public List<FlightDTO> getFlightsByOwner(@PathVariable Long ownerId) {
        return flightOwnerService.getFlightsByOwner(ownerId);
    }

    @PostMapping("/{ownerId}/flights")
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

    //SCHEDULE

    @GetMapping("/flights/{flightId}/schedules")
    public List<ScheduleDTO> getSchedulesByFlight(@PathVariable Long flightId) {
        return flightOwnerService.getSchedulesByFlight(flightId);
    }

    @PostMapping("/flights/{flightId}/schedules")
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

    //BOOKING

    @GetMapping("/{ownerId}/bookings")
    public List<BookingResponseDTO> getBookingsByOwner(@PathVariable Long ownerId) {
        return flightOwnerService.getBookingsByOwner(ownerId);
    }

    @PutMapping("/bookings/{bookingId}/refund")
    public void refundBooking(@PathVariable Long bookingId) {
        flightOwnerService.refundBooking(bookingId);
    }
}