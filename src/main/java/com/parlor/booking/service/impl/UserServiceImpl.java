package com.parlor.booking.service.impl;

import com.parlor.booking.domain.UserDto;
import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import com.parlor.booking.repository.UserRepository;
import com.parlor.booking.service.UserService;
import com.parlor.booking.service.exceptions.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        if (!userFromDB.isPresent()) {
            String errorMessage = "User with name:" + username + " was not found!";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
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

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

}
