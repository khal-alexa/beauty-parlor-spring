package com.parlor.booking.config;

import com.parlor.booking.entity.Role;
import com.parlor.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/", "/login", "/registration", "/css/*", "/js/*").permitAll()
                    .antMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                    .antMatchers("/client/**").hasAuthority(Role.CLIENT.name())
                    .antMatchers("/specialist/**").hasAuthority(Role.SPECIALIST.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                .and()
                    .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService);
    }
}
