package com.parlor.booking.service.impl;

import com.parlor.booking.domain.MainPageDto;
import com.parlor.booking.entity.Treatment;
import com.parlor.booking.repository.FeedbackRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.service.MainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {
    private final TreatmentRepository treatmentRepository;
    private final FeedbackRepository feedbackRepository;

    @Override
    public List<MainPageDto> getAllMainPageObjects() {
        List<Treatment> treatments = treatmentRepository.findAll();
        List<MainPageDto> dtos = new ArrayList<>();

        treatments.forEach(treatment -> treatment.getSpecialists()
                .forEach(specialist -> dtos.add(MainPageDto.builder()
                        .treatmentName(treatment.getName())
                        .price(treatment.getPrice())
                        .specialistName(specialist.getUsername())
                        .rate(feedbackRepository.findAvgRate(specialist.getId()))
                        .build())));
        return dtos;
    }
}
