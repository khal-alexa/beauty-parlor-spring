package com.parlor.booking.controller;

import com.parlor.booking.domain.TreatmentDto;
import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.User;
import com.parlor.booking.service.MainPageService;
import com.parlor.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MainPageService mainPageService;

    @GetMapping("/")
    public String indexPage(@PageableDefault(size = 5) Pageable pageable, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto user = (UserDto) authentication.getPrincipal();
            String path = user.getAuthorities().get(0).toString().toLowerCase();
            return "redirect:/" + path;
        }
        Page<TreatmentDto> page = mainPageService.getAllMainPageObjects(pageable);
        model.addAttribute("page", page);
        return "/main";
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/register")
    public String postRegisterPage(@Valid UserDto userDto, BindingResult bindingResult) {
        Optional<User> user = userService.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        String password = userDto.getPassword();
        if (password != null && !password.equals(userDto.getConfirmedPassword())) {
            bindingResult
                    .rejectValue("password", "error.user",
                            "Repeated password doesn't match");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.addNewUser(userDto);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String welcome() {
        return "register";
    }

}
