package com.parlor.booking.repository;

import com.parlor.booking.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByClientId(Long clientId, Pageable pageable);

    Page<Appointment> findAllBySpecialistId(Long specialistId, Pageable pageable);

    Page<Appointment> findAllByTreatmentId(Long serviceId, Pageable pageable);

    List<Appointment> findAllByDate(LocalDate date);

    List<Appointment> findAllByDateAndTreatmentId(LocalDate date, Long treatmentId);
}
