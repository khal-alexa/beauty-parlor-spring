package com.parlor.booking.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
public class MainPageController {
    @GetMapping("/")
    public ModelAndView mainPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @PostMapping("/client")
    public String showClientPage() {
        return "/client";
    }

    @GetMapping("/login")
    public ModelAndView afterLogin(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            log.error("Invalid Credentials provided.");
            model.addObject("loginError", "Invalid Credentials provided.");
            model.setViewName("error");
            return model;
        }
        log.info("Authorization successful");
        model.setViewName("client");
        return model;
    }

}
