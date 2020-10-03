package com.parlor.booking.service.impl;

import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.repository.TimeslotRepository;
import com.parlor.booking.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {
    private final TimeslotRepository timeslotRepository;

    @Override
    public List<Timeslot> findAll() {
        return timeslotRepository.findAll();
    }

}
