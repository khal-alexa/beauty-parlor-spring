package com.parlor.booking.controller;

import com.parlor.booking.domain.UserDto;
import com.parlor.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String welcome() {

        return "register";
    }

    @PostMapping("/register")
    public String saveNewUser(HttpServletRequest request){
        UserDto userDto = UserDto.builder()
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();
        userService.addNewUser(userDto);
        return "redirect:/login";
    }
}
