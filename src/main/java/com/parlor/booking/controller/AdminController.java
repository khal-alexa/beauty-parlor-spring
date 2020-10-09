package com.parlor.booking.controller;

import com.parlor.booking.domain.AppointmentDto;
import com.parlor.booking.service.AppointmentService;
import com.parlor.booking.service.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AppointmentService appointmentService;
    private final TimeslotService timeslotService;
    private static final String ADMIN_MAIN_PAGE = "admin/panel";

    @GetMapping()
    public String adminPage(@RequestParam(value = "startDate", required = false) String startDate,
                            Model model) {
        LocalDate date = parseDate(startDate);
        List<AppointmentDto> appointments = appointmentService.findAllByDateWithTimeslots(date, Optional.empty());
        model.addAttribute("appointments", appointments);
        return ADMIN_MAIN_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id,
                                  Model model) {
        model.addAttribute("appointment", appointmentService.findById(id));
        model.addAttribute("times", timeslotService.findAll());

        return "admin/edit_appointment";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/payed/{id}")
    public String changePaymentStatus(@PathVariable Long id) {
        appointmentService.markAppointmentAsPaid(id);
        return "redirect:/admin/edit/{id}";
    }

    @GetMapping("/saved/{id}")
    public String save(@RequestParam(value = "newTime",
                        required = false) String newTime,
                       @PathVariable Long id) {
        appointmentService.update(id, newTime);
        return "redirect:/admin";
    }

    private LocalDate parseDate(String startDate) {
        LocalDate date = LocalDate.now();
        if (startDate != null) {
            date = LocalDate.parse(startDate);
        }
        return date;
    }

}
