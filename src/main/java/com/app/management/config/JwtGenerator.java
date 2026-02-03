package com.app.management.config;

public interface JwtGenerator {
    
    String generateToken(String username);

    JwtInfo parseToken(String token);

}
