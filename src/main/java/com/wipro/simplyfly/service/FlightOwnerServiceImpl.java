package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.exceptions.BookingNotFoundException;
import com.wipro.simplyfly.exceptions.FlightNotFoundException;
import com.wipro.simplyfly.exceptions.ScheduleNotFoundException;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.FlightRepository;
import com.wipro.simplyfly.repository.RouteRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;

@Service
public class FlightOwnerServiceImpl implements FlightOwnerService {

    

    @Autowired
    private FlightOwnerRepository flightOwnerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private ISeatService seatService;
   

    // OWNER

    @Override
    public Long getCurrentOwnerId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (principal instanceof UserDetails) ? ((UserDetails)principal).getUsername() : principal.toString();

        return flightOwnerRepository.findByAccountEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner profile not found for email: " + email))
                .getId();
    }
    
    @Override
    public FlightOwner getOwnerById(Long ownerId) {
        return flightOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    @Override
    public List<FlightDTO> getFlightsByOwner(Long ownerId) {
        List<Flight> flights = flightRepository.findByFlightOwnerId(ownerId);
        return flights.stream()
                .map(flight -> new FlightDTO(
                        flight.getId(),
                        flight.getFlightNumber(),
                        flight.getFlightName(),
                        ownerId,
                        flight.getCheckInBaggage(),
                        flight.getCabinBaggage(),
                        flight.getRoute().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO addFlight(int routeId, Long ownerId, FlightDTO flightDTO) {

        FlightOwner owner = getOwnerById(ownerId);

        Route route = routeRepository.findById(routeId)
               .orElseThrow(() -> new RuntimeException("Route not found"));

        Flight flight = new Flight();

        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());
        flight.setFlightOwner(owner);
        flight.setRoute(route);

        Flight saved = flightRepository.save(flight);

        return new FlightDTO(
                saved.getId(),
                saved.getFlightNumber(),
                saved.getFlightName(),
                ownerId,
                saved.getCheckInBaggage(),
                saved.getCabinBaggage(),
                saved.getRoute().getId());
    }

    @Override
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());

        flightRepository.save(flight);

        flightDTO.setId(flightId);

        return flightDTO;
    }

    // Flight cannot be deleted if schedules exist
    @Override
    public void deleteFlight(Long flightId) {

        if (!flightRepository.existsById(flightId)) {
            throw new FlightNotFoundException("Flight not found");
        }

        if (scheduleRepository.existsByFlightId(flightId)) {
            throw new RuntimeException("Cannot delete flight because schedules exist");
        }

        flightRepository.deleteById(flightId);
    }

   
    // SCHEDULE
    
    @Override
    public List<ScheduleDTO> getSchedulesByFlight(Long flightId) {

        return scheduleRepository.findByFlightId(flightId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDepartureTime(schedule.getDepartureTime());
                    dto.setArrivalTime(schedule.getArrivalTime());
                    dto.setTotalSeats(schedule.getTotalSeats());
                    dto.setAvailableSeats(schedule.getAvailableSeats());
                    dto.setFare(schedule.getFare());
                    dto.setFlightId(schedule.getFlight().getId());

                    return dto;

                }).collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO addSchedule(Long flightId, ScheduleDTO scheduleDTO) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        Schedule schedule = new Schedule();

        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setArrivalTime(scheduleDTO.getArrivalTime());
        schedule.setTotalSeats(scheduleDTO.getTotalSeats());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());
        schedule.setFare(scheduleDTO.getFare());
        schedule.setFlight(flight);

        Schedule saved = scheduleRepository.save(schedule);

        seatService.createSeatsForSchedule(saved); 

        scheduleDTO.setId(saved.getId());
        scheduleDTO.setFlightId(flightId);

        return scheduleDTO;
    }

    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found"));

        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setArrivalTime(scheduleDTO.getArrivalTime());
        schedule.setTotalSeats(scheduleDTO.getTotalSeats());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());
        schedule.setFare(scheduleDTO.getFare());

        scheduleRepository.save(schedule);

        scheduleDTO.setId(scheduleId);

        return scheduleDTO;
    }

    // Schedule cannot be deleted if bookings exist
    @Override
    public void deleteSchedule(Long scheduleId) {

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleNotFoundException("Schedule not found");
        }

        if (bookingRepository.existsByScheduleId(scheduleId)) {
            throw new RuntimeException("Cannot delete schedule because bookings exist");
        }

        scheduleRepository.deleteById(scheduleId);
    }

    // BOOKING
  

    @Override
    public List<BookingResponseDTO> getBookingsByOwner(Long ownerId) {

        return bookingRepository.findBookingsByOwnerId(ownerId)
                .stream()
                .map(booking -> {

                    BookingResponseDTO dto = new BookingResponseDTO();

                    dto.setBookingId(booking.getId());
                    dto.setBookingReference(booking.getBookingReference());
                    dto.setNumberOfSeats(booking.getNumberOfSeats());
                    dto.setTotalAmount(booking.getTotalAmount());
                    dto.setBookingStatus(booking.getBookingStatus());
                    dto.setBookingDate(booking.getBookingDate());

                    return dto;

                }).collect(Collectors.toList());
    }

    // Refund only 75% and increase available seats
    @Override
    public void refundBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if ("REFUNDED".equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking already refunded");
        }

        double totalAmount = booking.getTotalAmount();

        double refundAmount = totalAmount * 0.75;

        booking.setTotalAmount(refundAmount);

        booking.setBookingStatus("REFUNDED");

        Schedule schedule = booking.getSchedule();

        schedule.setAvailableSeats(
                schedule.getAvailableSeats() + booking.getNumberOfSeats()
        );

        scheduleRepository.save(schedule);

        bookingRepository.save(booking);
    }

    
    // ROUTE
    
	@Override
	public RouteDTO addRoute(RouteDTO routeDTO) {
	    if (routeRepository.existsBySourceAndDestination(routeDTO.getSource(), routeDTO.getDestination())) {
	        throw new RuntimeException("Route from " + routeDTO.getSource() + 
	                                   " to " + routeDTO.getDestination() + " already exists!");
	    }

	    Route r = new Route();
	    r.setSource(routeDTO.getSource());
	    r.setDestination(routeDTO.getDestination());
	    r.setDistance(routeDTO.getDistance());
	    r.setEstimatedDuration(routeDTO.getEstimatedDuration());

	    Route saved = routeRepository.save(r);
	    routeDTO.setId(saved.getId());
	    return routeDTO;
	}
}