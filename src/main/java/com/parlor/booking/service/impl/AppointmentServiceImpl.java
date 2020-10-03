package com.parlor.booking.service.impl;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Appointment;
import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.repository.AppointmentRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.repository.TimeslotRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final TimeslotRepository timeslotRepository;
    private final UserRepository userRepository;
    private final TreatmentRepository treatmentRepository;

    @Override
    public Appointment saveNewAppointment(AppointmentDto appointmentDto) {
        return appointmentRepository.save(Appointment.builder()
                .date(appointmentDto.getDate())
                .timeslotId(timeslotRepository.findByStartTime(LocalTime.parse(appointmentDto.getTimeslot())).getId())
                .specialistId(userRepository.findByUsername(appointmentDto.getSpecialistName()).orElseThrow(EntityNotFoundException::new).getId())
                .treatmentId(treatmentRepository.findByName(appointmentDto.getTreatmentName()).orElseThrow(EntityNotFoundException::new).getId())
                .clientId(appointmentDto.getClientId())
                .isDone(false)
                .isPaid(false)
                .build());
    }

    @Override
    public List<AppointmentDto> findAllByDateWithTimeslots(LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findAllByDate(date).stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslotId))
                .collect(Collectors.toList());

        List<Timeslot> timeslots = timeslotRepository.findAll();

        List<AppointmentDto> dtos = new ArrayList<>();
        int index = 0;
        for (Timeslot timeslot : timeslots) {
            if (index < appointments.size() && timeslot.getId().equals(appointments.get(index).getTimeslotId())) {
                dtos.add(mapAppointmentIntoAppointmentDto(appointments.get(index)));
                index++;
            } else {
                AppointmentDto dto = AppointmentDto.builder()
                        .timeslot(timeslot.getStartTime().toString())
                        .date(date)
                        .available(true)
                        .build();
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public List<AppointmentDto> findAllBySpecialistIdAndDate(Long id, LocalDate date) {
        return appointmentRepository.findAllByDate(date).stream()
                .filter(appointment -> appointment.getSpecialistId().equals(id))
                .map(this::mapAppointmentIntoAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markAppointmentAsDone(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        appointment.orElseThrow(EntityNotFoundException::new).setDone(true);
        appointmentRepository.save(appointment.get());
    }

    private AppointmentDto mapAppointmentIntoAppointmentDto(Appointment appointment) {
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
                .available(false)
                .build();
    }

}
