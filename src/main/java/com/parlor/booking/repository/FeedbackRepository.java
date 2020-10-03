package com.parlor.booking.repository;

import com.parlor.booking.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAllByClientId(Long clientId, Pageable pageable);

    Page<Feedback> findAllBySpecialistId(Long specialistId, Pageable pageable);

    @Query("select AVG(rate) from Feedback where specialist_id=:id")
    Double findAvgRate(Long id);

}
