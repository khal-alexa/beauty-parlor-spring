package com.parlor.booking.service.impl;

import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.entity.Feedback;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.repository.FeedbackRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.exceptions.EntityNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceImplTest {
    private static final User USER = buildUser();
    private static final Feedback FEEDBACK_ENTITY = buildFeedback();
    private static final FeedbackDto CORRECT_FEEDBACK_DTO = buildCorrectFeedbackDto();
    private static final FeedbackDto WRONG_FEEDBACK_DTO = buildWrongFeedbackDto();
    private static final String CORRECT_SPECIALIST_NAME = "Edward";
    private static final String WRONG_SPECIALIST_NAME = "Andrew";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @Test
    public void saveFeedbackShouldThrowExceptionWhenSpecialistNotFound() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage(String.format("Specialist wih name %s was not found", WRONG_SPECIALIST_NAME));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        feedbackService.saveFeedback(WRONG_FEEDBACK_DTO);
    }

    @Test
    public void saveFeedbackShouldSaveWhenSpecialistFound() {
        when(userRepository.findByUsername(CORRECT_SPECIALIST_NAME)).thenReturn(Optional.of(USER));
        when(feedbackRepository.save(any())).thenReturn(FEEDBACK_ENTITY);

        Feedback actual = feedbackService.saveFeedback(CORRECT_FEEDBACK_DTO);

        assertEquals(FEEDBACK_ENTITY, actual);
    }

    private static User buildUser() {
        return User.builder()
                .id(1L)
                .username("Anna1234")
                .firstName("Anna")
                .lastName("Morgana")
                .email("anna@qmail.com")
                .password("annaPassword2@")
                .phoneNumber("+390832213453")
                .role(Role.CLIENT)
                .build();
    }

    private static Feedback buildFeedback() {
        return Feedback.builder()
                .id(5L)
                .rate(5)
                .clientId(5L)
                .createdOn(LocalDateTime.now())
                .text("Excellent!")
                .specialistId(1L)
                .build();
    }

    private static FeedbackDto buildCorrectFeedbackDto() {
        return FeedbackDto.builder()
                .id(5L)
                .rate(5)
                .clientId(5L)
                .text("Excellent!")
                .createdOn(LocalDateTime.now())
                .specialistName(CORRECT_SPECIALIST_NAME)
                .build();
    }

    private static FeedbackDto buildWrongFeedbackDto() {
        return FeedbackDto.builder()
                .id(5L)
                .rate(5)
                .clientId(5L)
                .text("Excellent!")
                .createdOn(LocalDateTime.now())
                .specialistName(WRONG_SPECIALIST_NAME)
                .build();
    }

}