package com.app.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private JwtGenerator jwtGenerator;

    public SecurityConfig(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(authenticationManager, jwtGenerator);

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/users/delete").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/expenses/createExpense").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/expenses").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/incomes/createIncome").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/incomes").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/categories/createCategory").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/expenses/byCategory").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
   
}
