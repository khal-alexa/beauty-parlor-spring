package com.parlor.booking.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Component
@Entity
@Table(name = "timeslots")
public class Timeslot {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

}
