package com.parlor.booking.service;

import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean addNewUser(UserDto userDto);

    Optional<User> findByEmail(String email);
}
