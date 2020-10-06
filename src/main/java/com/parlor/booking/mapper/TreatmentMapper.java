package com.parlor.booking.mapper;

import com.parlor.booking.domain.TreatmentDto;
import com.parlor.booking.entity.Treatment;
import com.parlor.booking.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreatmentMapper {
    Map<Long, Double> rates;

    public TreatmentMapper(Map<Long, Double> rates) {
        this.rates = rates;
    }

    public List<TreatmentDto> mapEntitiesIntoDtos(List<Treatment> treatments) {
        List<TreatmentDto> dtos = new ArrayList<>();
        treatments.forEach(treatment -> treatment.getSpecialists()
                .forEach(specialist -> dtos.add(TreatmentDto.builder()
                        .treatmentName(treatment.getName())
                        .price(treatment.getPrice())
                        .specialistName(specialist.getUsername())
                        .rate(rates.get(specialist.getId()))
                        .build())));
        return dtos;
    }

}
