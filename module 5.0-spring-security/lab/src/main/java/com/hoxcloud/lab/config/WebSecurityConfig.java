package com.hoxcloud.lab.config;
import com.hoxcloud.lab.service.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private static String[] publicRoute={"/login", "/api/auth/**", "/refresh"};
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoute).permitAll()
                        // Role-based authorization
                        .requestMatchers("/api/user/admin").hasRole("ADMIN")
                        .requestMatchers("/api/user/user").hasAnyRole("USER","CREATOR")
                        // Permission-based authorization
                        .requestMatchers(HttpMethod.GET, "/api/users/**")
                        .hasAuthority("READ_USER")

                        .requestMatchers(HttpMethod.POST, "/api/users/**")
                        .hasAuthority("WRITE_USER")

                        .requestMatchers(HttpMethod.DELETE, "/api/users/**")
                        .hasAuthority("DELETE_USER")
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);

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

