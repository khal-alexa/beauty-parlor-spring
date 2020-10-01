package com.parlor.booking.service.impl;

import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        if (!userFromDB.isPresent()) {
            log.error("user with name:" + username + " was not found!");
            throw new UsernameNotFoundException("user " + username + " was not found!");
        }
        return new UserDto(userFromDB.get());
    }

    @Override
    public boolean addNewUser(UserDto userDto) {
        User savedUser = userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(encoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .role(Role.CLIENT)
                .build());
        return savedUser.getId() != null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
