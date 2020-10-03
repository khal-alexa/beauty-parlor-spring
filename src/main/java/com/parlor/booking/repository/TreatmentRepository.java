package com.parlor.booking.repository;

import com.parlor.booking.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Optional<Treatment> findByName(String name);

    Optional<Treatment> findById(Long id);

}
