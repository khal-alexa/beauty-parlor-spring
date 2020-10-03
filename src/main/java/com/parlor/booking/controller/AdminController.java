package com.parlor.booking.controller;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AppointmentService appointmentService;

    @GetMapping()
    public String adminPage(Model model) {
        Optional<Object> attribute = Optional.ofNullable(model.getAttribute("date"));
        LocalDate date = LocalDate.now();
        if (attribute.isPresent()) {
            date = LocalDate.parse(attribute.toString());
        }
        List<AppointmentDto> appointments = appointmentService.findAllByDateWithTimeslots(date);
        model.addAttribute("appointments", appointments);
        return "admin/panel";
    }

    @GetMapping("/edit-appointment")
    public String editAppointment(Model model) {
        return "admin/edit_appointment";
    }

}
