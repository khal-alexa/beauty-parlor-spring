package com.parlor.booking.service.impl;

import com.parlor.booking.domain.TreatmentDto;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.Treatment;
import com.parlor.booking.entity.User;
import com.parlor.booking.mapper.TreatmentMapper;
import com.parlor.booking.repository.FeedbackRepository;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.MainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {
    private final TreatmentRepository treatmentRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public Page<TreatmentDto> getAllMainPageObjects(Pageable pageable) {
        List<Treatment> treatments = treatmentRepository.findAll();
        List<TreatmentDto> dtos = new TreatmentMapper(getAllRates(getAllSpecialists())).mapEntitiesIntoDtos(treatments);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        return new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());
    }

    private Map<Long, Double> getAllRates(List<User> specialists) {
        Map<Long, Double> rates = new HashMap<>();
        specialists
                .forEach(specialist -> rates.put(specialist.getId(),
                        feedbackRepository.findAvgRate(specialist.getId())));
        return rates;
    }

    private List<User> getAllSpecialists() {
       return userRepository.findAllByRole(Role.SPECIALIST);
    }

}
