package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightDTO;
import com.wipro.simplyfly.dto.ScheduleDTO;

public interface FlightOwnerService {

    //FLIGHT

    List<FlightDTO> getFlightsByOwner(Long ownerId);

    FlightDTO addFlight(Long ownerId, FlightDTO flightDTO);

    FlightDTO updateFlight(Long flightId, FlightDTO flightDTO);

    void deleteFlight(Long flightId);


    //SCHEDULE

    List<ScheduleDTO> getSchedulesByFlight(Long flightId);

    ScheduleDTO addSchedule(Long flightId, ScheduleDTO scheduleDTO);

    ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO);

    void deleteSchedule(Long scheduleId);


    //BOOKING

    List<BookingResponseDTO> getBookingsByOwner(Long ownerId);

    void refundBooking(Long bookingId);
}