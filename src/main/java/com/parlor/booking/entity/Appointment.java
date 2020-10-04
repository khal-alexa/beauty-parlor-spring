package com.parlor.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Component
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "timeslot_id")
    private Long timeslotId;

    @NotNull
    @Column
    private LocalDate date;

    @NotNull
    @Column(name = "client_id")
    private Long clientId;

    @NotNull
    @Column(name = "specialist_id")
    private Long specialistId;

    @NotNull
    @Column(name = "treatment_id")
    private Long treatmentId;

    @NotNull
    @Column(name = "is_paid")
    private boolean isPaid;

    @NotNull
    @Column(name = "is_done")
    private boolean isDone;

}
