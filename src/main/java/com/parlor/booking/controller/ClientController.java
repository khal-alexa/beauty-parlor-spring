package com.parlor.booking.controller;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.domain.FeedbackDto;
import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Appointment;
import com.parlor.booking.entity.Feedback;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.service.AppointmentService;
import com.parlor.booking.service.FeedbackService;
import com.parlor.booking.service.TimeslotService;
import com.parlor.booking.service.TreatmentService;
import com.parlor.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final FeedbackService feedbackService;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final TimeslotService timeslotService;
    private final TreatmentService treatmentService;

    private static final String CLIENT_BOOKING_PAGE = "client/booking";
    private static final String SPECIALISTS_LIST_ATTRIBUTE = "specialists";

    @GetMapping("/booking")
    public String showBookingOptions(Model model) {
        Object attribute = model.getAttribute("date");
        LocalDate date = LocalDate.now();
        if (attribute != null) {
            date = LocalDate.parse(attribute.toString());
        }
        List<AppointmentDto> appointments = appointmentService.findAllByDateWithTimeslots(date, Optional.empty());
        model.addAttribute("appointments", appointments);
        model.addAttribute("timeslots", timeslotService.findAll());
        model.addAttribute("treatments", treatmentService.findAll());
        model.addAttribute("date", date);
        return CLIENT_BOOKING_PAGE;
    }

    @GetMapping("/feedback")
    public ModelAndView showFeedbackForm() {
        ModelAndView modelAndView = new ModelAndView("client/feedback");
        List<User> specialists = userService.findAllByRole(Role.SPECIALIST);
        modelAndView.addObject(SPECIALISTS_LIST_ATTRIBUTE, specialists);
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
    public String showThanks() {
        return "client/thanks";
    }

    @GetMapping()
    public String clientProfile() {
        return "client/profile";
    }

    @GetMapping("/appointment")
    public String appointmentBookingPage(@RequestParam(value = "time") String time,
                                         @RequestParam(value = "date") String date,
                                         @RequestParam(value = "trName") String trName,
                                         Model model) {
        model.addAttribute("date", LocalDate.parse(date));
        model.addAttribute("time", time);
        model.addAttribute("treatmentName", trName);
        model.addAttribute(SPECIALISTS_LIST_ATTRIBUTE, userService.findAllByRole(Role.SPECIALIST)
                .stream()
                .filter(s -> s.getTreatments().contains(treatmentService.findByName(trName)
                        .orElseThrow(EntityNotFoundException::new)))
                .collect(Collectors.toList()));

        return "client/appointment";
    }

    @GetMapping("/booking/{treatmentName}")
    public String save(@PathVariable String treatmentName, Model model) {
        LocalDate date = getDateFromModel(model);
        List<AppointmentDto> appointments = appointmentService.findAllByDateAndTreatmentName(date, treatmentName);
        model.addAttribute("appointments", appointments);
        model.addAttribute("timeslots", timeslotService.findAll());
        model.addAttribute("treatmentName", treatmentName);
        model.addAttribute(SPECIALISTS_LIST_ATTRIBUTE, userService.findAllByRole(Role.SPECIALIST)
                .stream()
                .filter(s -> s.getTreatments().contains(treatmentService.findByName(treatmentName)
                        .orElseThrow(EntityNotFoundException::new)))
                .collect(Collectors.toList()));
        return CLIENT_BOOKING_PAGE;
    }

    @PostMapping("/appointment")
    public String save(@RequestParam(value = "time") String time,
                       @RequestParam(value = "date") String date,
                       @RequestParam(value = "treatmentName") String treatmentName,
                       String specialistName,
                       Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        Appointment newAppointment = appointmentService.saveNewAppointment(AppointmentDto.builder()
                .treatmentName(treatmentName)
                .timeslot(time)
                .date(LocalDate.parse(date))
                .specialistName(specialistName)
                .clientId(user.getId())
                .build());
        if (newAppointment.getId() == null) {
            throw new IllegalStateException("Appointment was not saved");
        }
        return "redirect:/client";
    }

    private LocalDate getDateFromModel(Model model) {
        Object attribute = model.getAttribute("date");
        LocalDate date = LocalDate.now();
        if (attribute != null) {
            date = LocalDate.parse(attribute.toString());
        }
        return date;
    }

}
