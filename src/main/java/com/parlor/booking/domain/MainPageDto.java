package com.parlor.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MainPageDto {
    private String treatmentName;
    private BigDecimal price;
    private String specialistName;
    private Double rate;

}
