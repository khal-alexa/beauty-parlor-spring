package com.parlor.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/booking")
    private String showBooking() {
        return "client/booking";
    }

    @PostMapping("/booking")
    private String booking() {
        return "client/booking";
    }

    @GetMapping("/feedback")
    private String showFeedbackForm() {
        return "client/feedback";
    }

    @PostMapping("/feedback")
    private String feedbackForm() {
        return "client/feedback";
    }

    @GetMapping()
    private String clientProfile() {
        return "client/profile";
    }

}
