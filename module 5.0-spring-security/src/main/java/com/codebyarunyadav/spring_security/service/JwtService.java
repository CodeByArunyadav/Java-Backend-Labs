package com.codebyarunyadav.spring_security.service;

import com.codebyarunyadav.spring_security.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                //.subject(String.valueOf(user.getId()))
                .subject(user.getEmail())
                //.claim("Email", user.getEmail())
                //.claim("role", Set.of("Admin", "User"))
                .claim("role",user.getRole())
                .claim("type","ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        String tokenUsername = extractUserName(token);
        String dbUsername = userDetails.getUsername();
        String tokenType = extractTokenType(token);
        boolean expired = isTokenExpired(token);

        System.out.println("Token Username : " + tokenUsername);
        System.out.println("DB Username    : " + dbUsername);
        System.out.println("Username Match : " + tokenUsername.equals(dbUsername));

        System.out.println("Token Type     : " + tokenType);
        System.out.println("Type Match     : " + "ACCESS".equals(tokenType));

        System.out.println("Expired        : " + expired);

        return tokenUsername.equals(dbUsername)
                && "ACCESS".equals(tokenType)
                && !expired;
    }


    public Claims extrectAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extrectAllClaims(token).getSubject();
    }

    public String extractUserId(String token)
    {
       return extrectAllClaims(token).getId();
    }

    public String extractTokenType(String token)
    {
        return extrectAllClaims(token).get("type",String.class);
    }


    private boolean isTokenExpired(String token) {

        return extrectAllClaims(token).getExpiration().before(new Date());
    }

    public String extractRole(String token) {
        return extrectAllClaims(token).get("role",String.class);
    }


}
