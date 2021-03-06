package com.parlor.booking.service.impl;

import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.entity.Feedback;
import com.parlor.booking.entity.User;
import com.parlor.booking.repository.FeedbackRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.FeedbackService;
import com.parlor.booking.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public Feedback saveFeedback(FeedbackDto feedbackDto) {
        Optional<User> specialist = userRepository.findByUsername(feedbackDto.getSpecialistName());
        if (!specialist.isPresent()) {
            String message = String.format("Specialist wih name %s was not found", feedbackDto.getSpecialistName());
            log.warn(message);
            throw new EntityNotFoundException(message);
        }
        return feedbackRepository.save(Feedback.builder()
                .rate(feedbackDto.getRate())
                .clientId(feedbackDto.getClientId())
                .specialistId(specialist.get().getId())
                .text(feedbackDto.getText())
                .createdOn(LocalDateTime.now())
                .build());
    }

    @Override
    public Double findSpecialistsRate(Long specialistId) {
        return feedbackRepository.findAvgRate(specialistId);
    }

}
