package com.parlor.booking.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT,
    ADMIN,
    SPECIALIST;

    @Override
    public String getAuthority() {
        return name();
    }
}
