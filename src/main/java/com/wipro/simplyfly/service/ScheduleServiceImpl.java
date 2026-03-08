package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.ScheduleDTO;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.repository.FlightRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FlightRepository flightRepository;

    // ✅ Add Schedule
    @Override
    public ScheduleDTO addSchedule(Long flightId, ScheduleDTO dto) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Schedule schedule = new Schedule();

        schedule.setFlight(flight);
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setFare(dto.getFare());
        schedule.setTotalSeats(dto.getTotalSeats());
        schedule.setAvailableSeats(dto.getAvailableSeats());

        Schedule saved = scheduleRepository.save(schedule);

        dto.setId(saved.getId());

        return dto;
    }

    // ✅ Update Schedule
    @Override
    public ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO dto) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setFare(dto.getFare());
        schedule.setTotalSeats(dto.getTotalSeats());
        schedule.setAvailableSeats(dto.getAvailableSeats());

        scheduleRepository.save(schedule);

        dto.setId(scheduleId);

        return dto;
    }

    // ✅ Delete Schedule
    @Override
    public void deleteSchedule(Long scheduleId) {

        if(!scheduleRepository.existsById(scheduleId)){
            throw new RuntimeException("Schedule not found");
        }

        scheduleRepository.deleteById(scheduleId);
    }

    // ✅ Get Schedules By Flight
    @Override
    public List<ScheduleDTO> getSchedulesByFlight(Long flightId) {

        return scheduleRepository.findByFlightId(flightId)
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDepartureTime(schedule.getDepartureTime());
                    dto.setArrivalTime(schedule.getArrivalTime());
                    dto.setFare(schedule.getFare());
                    dto.setTotalSeats(schedule.getTotalSeats());
                    dto.setAvailableSeats(schedule.getAvailableSeats());

                    if(schedule.getFlight()!=null){
                        dto.setFlightId(schedule.getFlight().getId());
                    }

                    return dto;

                }).collect(Collectors.toList());
    }

    // ✅ Get All Schedules
    @Override
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> {

                    ScheduleDTO dto = new ScheduleDTO();

                    dto.setId(schedule.getId());
                    dto.setDepartureTime(schedule.getDepartureTime());
                    dto.setArrivalTime(schedule.getArrivalTime());
                    dto.setFare(schedule.getFare());
                    dto.setTotalSeats(schedule.getTotalSeats());
                    dto.setAvailableSeats(schedule.getAvailableSeats());

                    if(schedule.getFlight()!=null){
                        dto.setFlightId(schedule.getFlight().getId());
                    }

                    return dto;

                }).collect(Collectors.toList());
    }
}