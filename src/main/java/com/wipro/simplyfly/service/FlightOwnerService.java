package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.FlightRepository;
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

    // ================= FLIGHT MANAGEMENT =================

    @Override
    public List<FlightDTO> getFlightsByOwner(Long ownerId) {

        return flightRepository.findByFlightOwnerId(ownerId)
                .stream()
                .map(flight -> {
                    FlightDTO dto = new FlightDTO();
                    dto.setId(flight.getId());
                    dto.setFlightName(flight.getFlightName());
                    dto.setFlightNumber(flight.getFlightNumber());
                    dto.setCheckInBaggage(flight.getCheckInBaggage());
                    dto.setCabinBaggage(flight.getCabinBaggage());
                    dto.setRoute(flight.getRoute());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO addFlight(Long ownerId, FlightDTO flightDTO) {

        FlightOwner owner = flightOwnerRepository.findById(ownerId).orElseThrow();

        Flight flight = new Flight();
        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());
        flight.setRoute(flightDTO.getRoute());
        flight.setFlightOwner(owner);

        Flight savedFlight = flightRepository.save(flight);

        flightDTO.setId(savedFlight.getId());
        return flightDTO;
    }

    @Override
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {

        Flight flight = flightRepository.findById(flightId).orElseThrow();

        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());
        flight.setRoute(flightDTO.getRoute());

        Flight updatedFlight = flightRepository.save(flight);

        flightDTO.setId(updatedFlight.getId());
        return flightDTO;
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    // ================= SCHEDULE MANAGEMENT =================

    @Override
    public List<ScheduleDTO> getSchedulesByFlight(Long flightId) {

        return scheduleRepository.findByFlightId(flightId)
                .stream()
                .map(schedule -> {
                    ScheduleDTO dto = new ScheduleDTO();
                    dto.setId(schedule.getId());
                    dto.setDepartureDate(schedule.getDepartureDate());
                    dto.setDepartureTime(schedule.getDepartureTime());
                    dto.setFare(schedule.getFare());
                    dto.setAvailableSeats(schedule.getAvailableSeats());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO addSchedule(Long flightId, ScheduleDTO scheduleDTO) {

        Flight flight = flightRepository.findById(flightId).orElseThrow();

        Schedule schedule = new Schedule();
        schedule.setDepartureDate(scheduleDTO.getDepartureDate());
        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setFare(scheduleDTO.getFare());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());
        schedule.setFlight(flight);
        schedule.setRoute(flight.getRoute());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        scheduleDTO.setId(savedSchedule.getId());
        return scheduleDTO;
    }

    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();

        schedule.setDepartureDate(scheduleDTO.getDepartureDate());
        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setFare(scheduleDTO.getFare());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        scheduleDTO.setId(updatedSchedule.getId());
        return scheduleDTO;
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    // ================= BOOKING MANAGEMENT =================

    @Override
    public List<BookingDTO> getBookingsByOwner(Long ownerId) {

        return bookingRepository.findBookingsByOwnerId(ownerId)
                .stream()
                .map(booking -> {
                    BookingDTO dto = new BookingDTO();
                    dto.setId(booking.getId());
                    dto.setBookingReference(booking.getBookingReference());
                    dto.setTotalAmount(booking.getTotalAmount());
                    dto.setBookingStatus(booking.getBookingStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void refundBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        booking.setBookingStatus("CANCELLED");

        bookingRepository.save(booking);
    }
}