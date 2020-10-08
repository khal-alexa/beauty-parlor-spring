package com.parlor.booking.mapper;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Appointment;
import com.parlor.booking.repository.TimeslotRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {
    private final TimeslotRepository timeslotRepository;
    private final TreatmentRepository treatmentRepository;
    private final UserRepository userRepository;

    public AppointmentDto mapAppointmentIntoAppointmentDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .timeslot(timeslotRepository.findById(appointment.getTimeslotId()).
                        orElseThrow(EntityNotFoundException::new).
                        getStartTime().toString())
                .treatmentName(treatmentRepository.findById(appointment.getTreatmentId())
                        .orElseThrow(EntityNotFoundException::new)
                        .getName())
                .specialistId(appointment.getSpecialistId())
                .specialistName(userRepository.findById(appointment.getSpecialistId())
                        .orElseThrow(EntityNotFoundException::new)
                        .getUsername())
                .isDone(appointment.isDone())
                .isPaid(appointment.isPaid())
                .available(false)
                .build();
    }

    public Appointment mapDtoIntoAppointment(AppointmentDto dto) {
        return Appointment.builder()
                .id(dto.getId())
                .timeslotId(timeslotRepository.findByStartTime(LocalTime.parse(dto.getTimeslot())).getId())
                .date(dto.getDate())
                .treatmentId(treatmentRepository.findByName(dto.getTreatmentName())
                        .orElseThrow(EntityNotFoundException::new).getId())
                .specialistId(userRepository.findByUsername(dto.getSpecialistName())
                        .orElseThrow(EntityNotFoundException::new).getId())
                .isPaid(dto.getIsPaid())
                .isDone(dto.getIsDone())
                .build();
    }

}
