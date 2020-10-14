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

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {
    private final TreatmentRepository treatmentRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public Page<TreatmentDto> getAllMainPageObjects(Pageable pageable, String sortField, String sortDirection) {
        if (sortField == null) {
            sortField = "specialistName";
            sortDirection = "ASC";
        }
        List<Treatment> treatments = treatmentRepository.findAll();
        List<TreatmentDto> dtos = new TreatmentMapper(getAllRates(getAllSpecialists())).mapEntitiesIntoDtos(treatments);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        List<TreatmentDto> sortedList = "Rate".equalsIgnoreCase(sortField) ?
                sortByRate(dtos, sortDirection) : sortBySpecialistName(dtos, sortDirection);
        return new PageImpl<>(sortedList.subList(start, end), pageable, dtos.size());
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

    private List<TreatmentDto> sortBySpecialistName(List<TreatmentDto> treatments, String sortDirection) {
        if ("DESC".equalsIgnoreCase(sortDirection)) {
            return treatments.stream()
                    .sorted(Collections.reverseOrder(Comparator.comparing(TreatmentDto::getSpecialistName)))
                    .collect(Collectors.toList());
        } else {
            return treatments.stream()
                    .sorted(Comparator.comparing(TreatmentDto::getSpecialistName))
                    .collect(Collectors.toList());
        }
    }

    private List<TreatmentDto> sortByRate(List<TreatmentDto> treatments, String sortDirection) {
        if ("DESC".equalsIgnoreCase(sortDirection)) {
            return treatments.stream()
                    .sorted(Collections.reverseOrder(Comparator.comparing(TreatmentDto::getRate)))
                    .collect(Collectors.toList());
        } else {
            return treatments.stream()
                    .sorted(Comparator.comparing(TreatmentDto::getRate))
                    .collect(Collectors.toList());
        }
    }

}
