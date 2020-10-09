package com.parlor.booking.service;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Timeslot;

import java.util.List;

public interface TimeslotService {
    List<Timeslot> findAll();

    List<AppointmentDto> findAllWithTreatment(Long treatmentId);

}
