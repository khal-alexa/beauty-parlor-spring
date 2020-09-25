package com.parlor.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {
    @GetMapping("/")
    public String welcome() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String performLogin(@PathVariable String username, @PathVariable String password) {
        return "/";
    }

}
