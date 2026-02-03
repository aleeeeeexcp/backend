package com.app.management.config;

import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import java.util.Collections   ;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends BasicAuthenticationFilter{

    private JwtGenerator jwtGenerator;

    public JwtFilter(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        super(authenticationManager);
        this.jwtGenerator = jwtGenerator;
        
    }

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        chain.doFilter(request, response);
        return;
    }

    try {
        String token = authHeader.substring(7);
        JwtInfo jwtInfo = jwtGenerator.parse(token);

        if (jwtInfo != null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                jwtInfo.getUserName(), 
                null, 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + jwtInfo.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        chain.doFilter(request, response);

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
    
    
}
