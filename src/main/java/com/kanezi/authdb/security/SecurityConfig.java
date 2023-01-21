package com.kanezi.authdb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // default form
//                .formLogin(Customizer.withDefaults())
                .formLogin(f -> f.loginPage("/login").usernameParameter("email"))
                .logout(l -> l.clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/"))
                .authorizeHttpRequests(r -> r.anyRequest().permitAll()//authenticated()
                )
                .build();
    }


    @Bean
    UserDetailsService inMemoryUserDetails() {
        User dummyUser = new User("user", passwordEncoder().encode("pass"), Collections.emptyList());
        return new InMemoryUserDetailsManager(dummyUser);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
