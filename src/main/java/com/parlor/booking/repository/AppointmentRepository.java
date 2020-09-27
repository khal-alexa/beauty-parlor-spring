package com.parlor.booking.repository;

import com.parlor.booking.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByClientId(Long clientId, Pageable pageable);

    Page<Appointment> findAllBySpecialistId(Long specialistId, Pageable pageable);

    Page<Appointment> findAllByServiceId(Long serviceId, Pageable pageable);

}
