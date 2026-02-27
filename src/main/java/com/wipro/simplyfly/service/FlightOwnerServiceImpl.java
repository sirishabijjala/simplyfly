package com.wipro.simplyfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.FlightRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.service.FlightOwnerService;

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

    // ================= FLIGHT =================

    @Override
	public List<FlightDTO> getFlightsByOwner(Long ownerId){
        return flightRepository.findByFlightOwnerId(ownerId);
    }

    @Override
    public Flight addFlight(Long ownerId, Flight flight) {

        FlightOwner owner = flightOwnerRepository.findById(ownerId).orElseThrow();

        flight.setFlightOwner(owner);

        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Long flightId, Flight flight) {

        Flight existingFlight = flightRepository.findById(flightId).orElseThrow();

        existingFlight.setFlightName(flight.getFlightName());
        existingFlight.setFlightNumber(flight.getFlightNumber());
        existingFlight.setCheckInBaggage(flight.getCheckInBaggage());
        existingFlight.setCabinBaggage(flight.getCabinBaggage());
        existingFlight.setRoute(flight.getRoute());

        return flightRepository.save(existingFlight);
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    // ================= SCHEDULE =================

    @Override
    public List<Schedule> getSchedulesByFlight(Long flightId) {
        return scheduleRepository.findByFlightId(flightId);
    }

    @Override
    public Schedule addSchedule(Long flightId, Schedule schedule) {

        Flight flight = flightRepository.findById(flightId).orElseThrow();

        schedule.setFlight(flight);

        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(Long scheduleId, Schedule schedule) {

        Schedule existingSchedule = scheduleRepository.findById(scheduleId).orElseThrow();

        existingSchedule.setDepartureDate(schedule.getDepartureDate());
        existingSchedule.setDepartureTime(schedule.getDepartureTime());
        existingSchedule.setFare(schedule.getFare());
        existingSchedule.setAvailableSeats(schedule.getAvailableSeats());

        return scheduleRepository.save(existingSchedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    // ================= BOOKING =================

    @Override
    public List<Booking> getBookingsByOwner(Long ownerId) {
        return bookingRepository.findBookingsByOwnerId(ownerId);
    }

    @Override
    public void refundBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        booking.setBookingStatus("CANCELLED");

        bookingRepository.save(booking);
    }
}