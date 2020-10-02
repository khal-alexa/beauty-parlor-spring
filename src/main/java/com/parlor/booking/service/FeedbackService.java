package com.parlor.booking.service;

import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.entity.Feedback;

public interface FeedbackService {
    Feedback saveFeedback(FeedbackDto feedbackDto);
}
