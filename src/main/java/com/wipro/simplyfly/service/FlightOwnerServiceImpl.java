package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;
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

                    FlightOwner owner = flight.getFlightOwner();

                    FlightOwnerDTO ownerDTO = new FlightOwnerDTO(
                            owner.getId(),
                            owner.getName(),
                            owner.getEmail()
                    );

                    dto.setFlightownerdto(ownerDTO);

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
        flight.setFlightOwner(owner);

        Flight saved = flightRepository.save(flight);

        flightDTO.setId(saved.getId());

        return flightDTO;
    }

    @Override
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {

        Flight flight = flightRepository.findById(flightId).orElseThrow();

        flight.setFlightName(flightDTO.getFlightName());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setCheckInBaggage(flightDTO.getCheckInBaggage());
        flight.setCabinBaggage(flightDTO.getCabinBaggage());

        flightRepository.save(flight);

        return flightDTO;
    }

    @Override
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    // ================= SCHEDULE =================

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
                    dto.setFlightId(schedule.getFlight().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO addSchedule(Long flightId, ScheduleDTO scheduleDTO) {

        Flight flight = flightRepository.findById(flightId).orElseThrow();

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

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();

        schedule.setDepartureTime(scheduleDTO.getDepartureTime());
        schedule.setArrivalTime(scheduleDTO.getArrivalTime());
        schedule.setTotalSeats(scheduleDTO.getTotalSeats());
        schedule.setAvailableSeats(scheduleDTO.getAvailableSeats());

        scheduleRepository.save(schedule);

        return scheduleDTO;
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    // ================= BOOKING =================

    @Override
    public List<BookingResponseDTO> getBookingsByOwner(Long ownerId) {

        return bookingRepository.findBookingsByOwnerId(ownerId)
                .stream()
                .map(booking -> {

                    BookingResponseDTO dto = new BookingResponseDTO();
                    dto.setBookingId(booking.getId());
                    dto.setBookingReference(booking.getBookingReference());
                    dto.setFlightName(booking.getFlight().getFlightName());
                    dto.setOrigin(booking.getFlight().getRoute().getSource());
                    dto.setDestination(booking.getFlight().getRoute().getDestination());
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

        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        booking.setBookingStatus("CANCELLED");

        bookingRepository.save(booking);
    }
}
