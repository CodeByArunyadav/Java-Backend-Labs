package com.hoxcloud.lab.service;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String userName = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);

                    userName = jwtService.extractUserName(token);

            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userService.loadUserByUsername(userName);

                System.out.println("Username: " + userName);
                System.out.println("Validate Token: " + jwtService.validateToken(token, userDetails));
                System.out.println("Authorities: " + userDetails.getAuthorities());

                if (jwtService.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("Authentication: "
                            + SecurityContextHolder.getContext().getAuthentication());
                }
            }
        filterChain.doFilter(request, response);

        }catch (JwtException ex)
        {
            handlerExceptionResolver.resolveException(request,response,null,ex);
            return;
        }

    }
}
