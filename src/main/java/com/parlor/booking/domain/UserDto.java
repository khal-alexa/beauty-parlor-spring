package com.parlor.booking.domain;

import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmedPassword;
    private String email;
    private String phoneNumber;
    private List<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.authorities = new ArrayList<>();
        authorities.add(user.getRole());
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.enabled = true;
        this.credentialsNonExpired = true;
    }

}
