package com.parlor.booking.controller;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Appointment;
import com.parlor.booking.entity.Feedback;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.Timeslot;
import com.parlor.booking.entity.User;
import com.parlor.booking.service.AppointmentService;
import com.parlor.booking.service.FeedbackService;
import com.parlor.booking.service.TimeslotService;
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
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final FeedbackService feedbackService;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final TimeslotService timeslotService;

    @GetMapping("/booking")
    private String showBooking(Model model) {
        Object attribute = model.getAttribute("date");
        LocalDate date = LocalDate.now();
        if (attribute != null) {
            date = LocalDate.parse(attribute.toString());
        }
        List<AppointmentDto> appointments = appointmentService.findAllByDateWithTimeslots(date);
        List<Timeslot> timeslots = timeslotService.findAll();
        model.addAttribute("appointments", appointments);
        model.addAttribute("timeslots", timeslots);
        return "client/booking";
    }

    @GetMapping("/appointments")
    private String showAppointments() {
        return "client/appointments";
    }

    @PostMapping("/booking")
    private String booking(@Valid AppointmentDto appointmentDto, Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        appointmentDto.setClientId(user.getId());
        Appointment appointment = appointmentService.saveNewAppointment(appointmentDto);
        if (appointment == null || appointment.getId() == null) {
            throw new IllegalStateException("Appointment was not saved");
        }
        return "redirect:/client/appointments";
    }

    @GetMapping("/feedback")
    public ModelAndView showFeedbackForm() {
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
