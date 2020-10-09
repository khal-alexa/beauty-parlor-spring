package com.parlor.booking.service;

import com.parlor.booking.entity.Treatment;

import java.util.List;
import java.util.Optional;

public interface TreatmentService {
    List<Treatment> findAll();

    Optional<Treatment> findByName(String treatmentName);
}
