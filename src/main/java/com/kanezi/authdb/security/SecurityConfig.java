package com.kanezi.authdb.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(Customizer.withDefaults())
                //                .formLogin(Customizer.withDefaults())
                .formLogin(f -> f.loginPage("/login")
                                 .usernameParameter("email"))
                .oauth2Login(oc -> oc.loginPage("/login"))
                .logout(l -> l.clearAuthentication(true)
                              .invalidateHttpSession(true)
                              .logoutSuccessUrl("/"))
                .authorizeHttpRequests(r -> r.requestMatchers("/webjars/**", "/", "/favicon.ico", "/login", "/register", "/error", "/toast")
                                             .permitAll()
                                             .anyRequest()
                                             .authenticated()
                )
                .build();
    }


/*    @Bean
    UserDetailsService inMemoryUserDetails() {
        User dummyUser = new User("user", passwordEncoder().encode("pass"), Collections.emptyList());

        UserDetails second = User.builder()
                                 .username("second")
                                 .password(passwordEncoder().encode("1234"))
                                 .authorities(Collections.emptyList())
                                 .build();

        log.info("user => {}", dummyUser);
        log.info("second user => {}", second);

        return new InMemoryUserDetailsManager(dummyUser, second);
//        return new InMemoryUserDetailsManager(dummyUser);

    }*/

/*    @Bean
    UserDetailsService springProvidedDbUserDetails(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        UserDetails third = User.builder()
                                 .username("third")
                                 .password(passwordEncoder().encode("1234"))
                                 .authorities(Collections.emptyList())
                                 .build();
        jdbcUserDetailsManager.createUser(third);
        return jdbcUserDetailsManager;

    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    ApplicationListener<AuthenticationSuccessEvent> logAuthenticationSuccessEvent() {
        return event -> log.info("User logged in: {}", event.getAuthentication());
    }
}
