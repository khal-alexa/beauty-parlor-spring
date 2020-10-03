package com.parlor.booking.service.impl;

import com.parlor.booking.entity.Treatment;
import com.parlor.booking.repository.TreatmentRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;

    @Override
    public List<Treatment> findAll() {
        return treatmentRepository.findAll();
    }

}
