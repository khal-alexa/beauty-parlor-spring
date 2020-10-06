package com.parlor.booking.service;

import com.parlor.booking.domain.TreatmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainPageService {
    Page<TreatmentDto> getAllMainPageObjects(Pageable pageable);

}
