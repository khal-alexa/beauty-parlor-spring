package com.parlor.booking.domain;

import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.entity.Treatment;
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
        private String timeslot;
        private LocalDate date;
        private String specialistName;
        private String treatmentName;
        private Long clientId;
        private String clientName;
        private Boolean available;

}
