package com.parlor.booking.controller;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.domain.UserDto;
import com.parlor.booking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/specialist")
public class SpecialistController {
    private final AppointmentService appointmentService;

    @GetMapping()
    public String specialistCabinet(@RequestParam(required = false) Long appointmentId,
                                    @RequestParam(value = "startDate", required = false) String startDate,
                                    Model model,
                                    Authentication authentication) {
        if (appointmentId != null) {
            appointmentService.markAppointmentAsDone(appointmentId);
        }

        LocalDate date = startDate == null ? LocalDate.now() : LocalDate.parse(startDate);
        UserDto user = (UserDto) authentication.getPrincipal();
        List<AppointmentDto> appointments = appointmentService.findAllBySpecialistIdAndDate(user.getId(), date);
        model.addAttribute("appointments", appointments);
        return "specialist/cabinet";
    }

    @PostMapping()
    public String changeAppointmentStatus(@RequestParam Long appointmentId, Model model) {
        appointmentService.markAppointmentAsDone(appointmentId);
        return "specialist/cabinet";
    }

}
