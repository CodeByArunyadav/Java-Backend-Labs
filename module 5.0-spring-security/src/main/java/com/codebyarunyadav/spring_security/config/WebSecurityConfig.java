package com.codebyarunyadav.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        httpSecurity.authorizeHttpRequests(auth-> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/api/user/admin").hasRole("Admin")
                .requestMatchers("/api/user/user").hasRole("User")
                .anyRequest().authenticated()
       ).formLogin(Customizer.withDefaults())
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }


    @Bean
    UserDetailsService mymemoryUserDeltails()
    {
        UserDetails users= User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("User")
                .build();

        UserDetails admin= User
                .withUsername("Admin")
                .password(passwordEncoder().encode("admin"))
                .roles("Admin")
                .build();

        return new InMemoryUserDetailsManager(users,admin);
    }




    @Bean
public  PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}

