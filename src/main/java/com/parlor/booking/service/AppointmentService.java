package com.parlor.booking.service;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment saveNewAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> findAllByDateWithTimeslots(LocalDate date);
}
