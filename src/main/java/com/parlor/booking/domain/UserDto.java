package com.parlor.booking.domain;

import com.parlor.booking.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private final Role role = Role.CLIENT;

}
