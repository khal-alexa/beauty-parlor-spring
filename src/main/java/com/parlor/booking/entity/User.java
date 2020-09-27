package com.parlor.booking.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Component
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column
    @NotNull
    private Role role;

}