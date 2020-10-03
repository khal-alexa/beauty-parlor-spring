package com.parlor.booking.repository;

import com.parlor.booking.entity.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    Timeslot findByStartTime(LocalTime time);
}
