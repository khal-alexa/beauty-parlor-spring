package com.parlor.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {
    private String name;
    private String specialistName;
    private BigDecimal price;
    private double rating;

}
