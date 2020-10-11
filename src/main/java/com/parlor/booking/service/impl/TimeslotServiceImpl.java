package com.parlor.booking.service.impl;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.repository.TimeslotRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.service.TimeslotService;
import com.parlor.booking.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {
    private final TimeslotRepository timeslotRepository;
    private final TreatmentRepository treatmentRepository;

    @Override
    public List<Timeslot> findAll() {
        return timeslotRepository.findAll();
    }

    @Override
    public List<AppointmentDto> findAllWithTreatment(Long treatmentId) {
        List<Timeslot> allTimeslots = findAll();
        return allTimeslots.stream()
                .map(timeslot -> AppointmentDto.builder()
                        .timeslot(timeslot.getStartTime().toString())
                        .date(LocalDate.now())
                        .treatmentName(treatmentRepository.findById(treatmentId)
                                .orElseThrow(EntityNotFoundException::new).getName())
                        .build())
                .collect(Collectors.toList());
    }

}
