package com.parlor.booking.controller;

import com.parlor.booking.repository.AppointmentRepository;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.AppointmentService;
import com.parlor.booking.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(SpecialistController.class)
public class SpecialistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    SpecialistController specialistController;

    @Test
    public void unauthorizedUserShouldBeRedirectedToLoginPage() throws Exception {
        mockMvc.perform(get("/specialist"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

}