package com.parlor.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/specialist")
public class SpecialistController {

    @GetMapping()
    public String specialistCabinet() {
        return "specialist/cabinet";
    }

}
