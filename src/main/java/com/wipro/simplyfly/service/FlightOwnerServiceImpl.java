package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
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
    private FlightRepository flightRepository;

    @Autowired
    private FlightOwnerRepository flightOwnerRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RouteRepository routeRepository;

    //FLIGHT

    @Override
    public FlightDTO addFlight(Long ownerId, FlightDTO flightDTO) {

        FlightOwner owner = flightOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new FlightNotFoundException(
                        "Owner not found with id: " + ownerId));
        Route route=routeRepository.findById(flightDTO.getRouteId()).orElseThrow(()-> new RuntimeException("Routes Not Found"));

        Flight flight = new Flight();
        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());
        flight.setFlightOwner(owner);
        flight.setRoute(route);
      

        Flight saved = flightRepository.save(flight);

        flightDTO.setId(saved.getId());

    //    return flightDTO;
        return new FlightDTO(
                saved.getId(),
                saved.getFlightNumber(),
                saved.getFlightName(),
                saved.getFlightOwner().getId(),   // ⭐ set owner id
                saved.getCheckInBaggage(),
                saved.getCabinBaggage(),
                saved.getRoute().getId()
        );
    }

    @Override
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(
                        "Flight not found with id: " + flightId));

        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());

        flightRepository.save(flight);

        return flightDTO;
    }

    @Override
    public void deleteFlight(Long flightId) {

        if (!flightRepository.existsById(flightId)) {
            throw new FlightNotFoundException(
                    "Flight not found with id: " + flightId);
        }

        flightRepository.deleteById(flightId);
    }

    //SCHEDULE

    @Override
    public List<ScheduleDTO> getSchedulesByFlight(Long flightId) {

        return scheduleRepository.findByFlightRouteId(flightId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setId(schedule.getId());
                    dto.setDepartureTime(schedule.getDepartureTime());
                    dto.setArrivalTime(schedule.getArrivalTime());
                    dto.setTotalSeats(schedule.getTotalSeats());
                    dto.setAvailableSeats(schedule.getAvailableSeats());
                    dto.setFlightId(schedule.getFlight().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO addSchedule(Long flightId, ScheduleDTO scheduleDTO) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(
                        "Flight not found with id: " + flightId));

        Schedule schedule = new Schedule();
        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setArrivalTime(scheduleDTO.getArrivalTime());
        schedule.setTotalSeats(scheduleDTO.getTotalSeats());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());
        schedule.setFlight(flight);

        Schedule saved = scheduleRepository.save(schedule);

        scheduleDTO.setId(saved.getId());
        scheduleDTO.setFlightId(flightId);

        return scheduleDTO;
    }

    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(
                        "Schedule not found with id: " + scheduleId));

        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setArrivalTime(scheduleDTO.getArrivalTime());
        schedule.setTotalSeats(scheduleDTO.getTotalSeats());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());

        scheduleRepository.save(schedule);

        return scheduleDTO;
    }

    @Override
    public void deleteSchedule(Long scheduleId) {

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ScheduleNotFoundException(
                    "Schedule not found with id: " + scheduleId);
        }

        scheduleRepository.deleteById(scheduleId);
    }

    //BOOKING

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
                })
                .collect(Collectors.toList());
    }

    @Override
    public void refundBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Booking not found with id: " + bookingId));

        booking.setBookingStatus("CANCELLED");

        bookingRepository.save(booking);
    }

	@Override
	public List<FlightDTO> getFlightsByOwner(Long ownerId) {
		// TODO Auto-generated method stub
		return null;
	}
}