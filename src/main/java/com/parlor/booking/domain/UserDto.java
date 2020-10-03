package com.parlor.booking.domain;

import com.parlor.booking.entity.Role;
import com.parlor.booking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private Long id;

    @NotEmpty(message = "Please enter username")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,19}$", message = "Username can contain only english letters " +
            "or numbers or symbols: _-. (min - 4, max - 20), " +
            " should start from letter")
    private String username;

    @NotEmpty(message = "Please enter first name")
    private String firstName;

    @NotEmpty(message = "Please enter last name")
    private String lastName;

    @ToString.Exclude
    @NotEmpty(message = "Please enter password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password requirements:\n" +
            "        - Minimum 8 characters\n" +
            "        - At least one uppercase character\n" +
            "        - At least one lowercase character\n" +
            "        - At least one digit")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @NotEmpty(message = "Please confirm password")
    private String confirmedPassword;

    @NotEmpty(message = "Please enter email")
    @Pattern(regexp = "^\\w+\\.?\\w+@\\w+\\.[a-z]{2,4}$", message = "Email is not correct")
    private String email;

    @NotEmpty(message = "Please enter phone number")
    @Pattern(regexp = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$")
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
