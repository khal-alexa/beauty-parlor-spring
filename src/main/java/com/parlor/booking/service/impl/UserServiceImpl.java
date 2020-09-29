package com.parlor.booking.service.impl;

import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        User userFromDB = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("user " + username + " was not found!"));
        return new UserDto(userFromDB);
    }

    @Override
    public boolean addNewUser(UserDto userDto) {
        User savedUser = userRepository.save(User.builder()
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .email(userDto.getEmail())
        .phoneNumber(userDto.getPhoneNumber())
        .role(Role.CLIENT)
        .build());
        return savedUser.getId() !=null;
    }

}
