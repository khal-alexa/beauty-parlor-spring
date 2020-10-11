package com.parlor.booking.service.impl;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Appointment;
import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.entity.Treatment;
import com.parlor.booking.mapper.AppointmentMapper;
import com.parlor.booking.repository.AppointmentRepository;
import com.parlor.booking.repository.TimeslotRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.service.AppointmentService;
import com.parlor.booking.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TreatmentRepository treatmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Appointment saveNewAppointment(AppointmentDto appointmentDto) {
        return appointmentRepository.save(appointmentMapper.mapDtoIntoAppointment(appointmentDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> findAllByDateWithTimeslots(LocalDate date, Optional<Long> treatmentId) {
        List<Appointment> appointments = findAllSorted(date, treatmentId);

        List<Timeslot> timeslots = timeslotRepository.findAll();

        List<AppointmentDto> dtos = new ArrayList<>();
        int index = 0;
        for (Timeslot timeslot : timeslots) {
            if (index < appointments.size() && timeslot.getId().equals(appointments.get(index).getTimeslotId())) {
                dtos.add(appointmentMapper.mapAppointmentIntoAppointmentDto(appointments.get(index)));
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

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> findAllBySpecialistIdAndDate(Long id, LocalDate date) {
        return appointmentRepository.findAllByDate(date).stream()
                .filter(appointment -> appointment.getSpecialistId().equals(id))
                .map(appointmentMapper::mapAppointmentIntoAppointmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markAppointmentAsDone(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        appointment.orElseThrow(EntityNotFoundException::new).setDone(true);
        appointmentRepository.save(appointment.orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AppointmentDto findById(Long appointmentId) {
        return appointmentMapper.
                mapAppointmentIntoAppointmentDto(appointmentRepository.findById(appointmentId)
                        .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void update(Long id, String time) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        appointment.setTimeslotId(timeslotRepository.findByStartTime(LocalTime.parse(time)).getId());
        appointmentRepository.save(appointment);
    }

    @Override
    public void markAppointmentAsPaid(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        appointment.orElseThrow(EntityNotFoundException::new).setPaid(true);
        appointmentRepository.save(appointment.orElseThrow(EntityNotFoundException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> findAllByDateAndTreatmentName(LocalDate date, String treatmentName) {
        Treatment treatment = treatmentRepository.findByName(treatmentName).orElseThrow(EntityNotFoundException::new);
        return findAllByDateWithTimeslots(date, Optional.of(treatment.getId()));
    }

    private List<Appointment> findAllSorted(LocalDate date, Optional<Long> treatmentId) {
        List<Appointment> appointments;
        if(!treatmentId.isPresent()) {
            appointments = appointmentRepository.findAllByDate(date);
        } else {
            appointments = appointmentRepository.findAllByDateAndTreatmentId(date, treatmentId.get());
        }
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslotId))
                .collect(Collectors.toList());
    }

}
