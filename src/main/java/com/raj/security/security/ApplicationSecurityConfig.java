package com.raj.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.raj.security.security.ApplicationUserPermission.*;
import static com.raj.security.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAnyAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails studentUser = User.builder().username("student")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())
                .build();
        UserDetails adminUser = User.builder().username("admin")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMIN.name())
                .build();
        UserDetails adminTraineeUser = User.builder().username("admin")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMINTRAINEE.name())
                .build();
        return new InMemoryUserDetailsManager(studentUser, adminUser);
    }
}
