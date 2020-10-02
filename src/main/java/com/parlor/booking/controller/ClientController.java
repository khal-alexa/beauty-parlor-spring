package com.parlor.booking.controller;

import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Feedback;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.service.FeedbackService;
import com.parlor.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final FeedbackService feedbackService;
    private final UserService userService;

    @GetMapping("/booking")
    private String showBooking() {
        return "client/booking";
    }

    @PostMapping("/booking")
    private String booking() {
        return "client/booking";
    }

    @GetMapping("/feedback")
    public ModelAndView showFeedbackForm(Model model) {
        ModelAndView modelAndView = new ModelAndView("client/feedback");
        List<User> specialists = userService.findAllByRole(Role.SPECIALIST);
        modelAndView.addObject("specialists", specialists);
        return modelAndView;
    }

    @PostMapping("/feedback")
    public String saveFeedback(@Valid FeedbackDto feedbackDto, Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        feedbackDto.setClientId(user.getId());
        Feedback feedback = feedbackService.saveFeedback(feedbackDto);
        if (feedback == null || feedback.getId() == null) {
            throw new IllegalStateException("Feedback was not saved");
        }
        return "redirect:/client/thanks";
    }

    @GetMapping("/thanks")
    private String showThanks() {
        return "client/thanks";
    }

    @GetMapping()
    private String clientProfile() {
        return "client/profile";
    }

}
