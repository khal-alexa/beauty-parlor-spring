package com.parlor.booking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
        private Long id;
        private String timeslot;
        private LocalDate date;
        private Long specialistId;
        private String specialistName;
        private String treatmentName;
        private Long clientId;
        private String clientName;
        private Boolean available;
        private Boolean isDone;
        private Boolean isPaid;

}
