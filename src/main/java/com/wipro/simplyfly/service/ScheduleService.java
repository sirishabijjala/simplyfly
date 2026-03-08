package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.ScheduleDTO;

public interface ScheduleService {

    ScheduleDTO addSchedule(Long flightId, ScheduleDTO dto);

    ScheduleDTO updateSchedule(Long scheduleId, ScheduleDTO dto);

    void deleteSchedule(Long scheduleId);

    List<ScheduleDTO> getSchedulesByFlight(Long flightId);

    List<ScheduleDTO> getAllSchedules();
}