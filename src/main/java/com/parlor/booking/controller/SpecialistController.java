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
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/specialist")
public class SpecialistController {
    private final AppointmentService appointmentService;

    @GetMapping()
    public String specialistCabinet(Model model, Authentication authentication) {
        Optional<Object> attribute = Optional.ofNullable(model.getAttribute("date"));
        LocalDate date = LocalDate.now();
        if (attribute.isPresent()) {
            date = LocalDate.parse(attribute.toString());
        }
            UserDto user = (UserDto) authentication.getPrincipal();
        List<AppointmentDto> appointments = appointmentService.findAllBySpecialistIdAndDate(user.getId(), date);
        model.addAttribute("appointments", appointments);
        return "specialist/cabinet";
    }

    @GetMapping("/done")
    public String changeAppointmentStatus(Model model) {
        AppointmentDto appointmentDto = (AppointmentDto) model.getAttribute("appointment");
        appointmentService.markAppointmentAsDone(appointmentDto.getId());
        return "specialist/cabinet";
    }


}
