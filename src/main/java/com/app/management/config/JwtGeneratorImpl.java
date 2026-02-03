package com.app.management.config;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtGeneratorImpl implements JwtGenerator {

    private final long expirationMinutes = 60; // Token expiration time in minutes
    
    @Override
    public String generateToken(String username) {
        
        return Jwts.builder()
                .claim("username", username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinutes*60*1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "secretkey")
                .compact();
    }

    @Override
    public JwtInfo parseToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey("secretkey")
                .parseClaimsJws(token)
                .getBody();
        return new JwtInfo(null, claims.get("username", String.class));
    
    }
    
}
