package com.codebyarunyadav.spring_security.config;

import com.codebyarunyadav.spring_security.service.JwtAuthFilter;
import com.codebyarunyadav.spring_security.service.JwtService;
import com.codebyarunyadav.spring_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/api/auth/**").permitAll()
                        .requestMatchers("/api/user/admin").hasRole("Admin")
                        .requestMatchers("/api/user/user").hasRole("User")
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
//    @Bean
//    UserDetailsService mymemoryUserDeltails()
//    {
//        UserDetails users= User
//                .withUsername("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("User")
//                .build();
//
//        UserDetails admin= User
//                .withUsername("Admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(users,admin);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }


}

