package com.parlor.booking.controller;

import com.parlor.booking.domain.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String welcome() {

        return "register";
    }

    @PostMapping("/register")
    public void saveData(HttpServletRequest request){
        String name = request.getParameter("uname");
        String password = request.getParameter("psw");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        UserDto userDto = new UserDto(name, password, email, phoneNumber);

    }
}
