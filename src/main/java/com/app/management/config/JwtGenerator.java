package com.app.management.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
    
    
    private String signKey;

        
    private long expirationTime;

    public JwtGenerator(@Value("${jwt.sign.key}") String signKey, @Value("${jwt.expiration.time}") long expirationTime) {
        this.signKey = signKey;
        this.expirationTime = expirationTime;
    }

    public String generate(JwtInfo jwtInfo) {
        return Jwts.builder()
        .setSubject(jwtInfo.getUserName())
        .claim("userId", jwtInfo.getUserId())
        .claim("roles", jwtInfo.getRole())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime*60*1000))
        .signWith(SignatureAlgorithm.HS512, signKey.getBytes())    
        .compact();
    }

    public JwtInfo parse(String token) {
        
        Claims claims = Jwts.parser()
        .setSigningKey(signKey.getBytes())
        .parseClaimsJws(token)
        .getBody();

        return new JwtInfo(
            claims.get("userId", String.class),
            claims.getSubject(),
            (String) claims.get("roles")
        );
    }
}