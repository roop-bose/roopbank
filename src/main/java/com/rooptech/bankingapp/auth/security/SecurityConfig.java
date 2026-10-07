package com.rooptech.bankingapp.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // =====================================================
    // Password Encoder
    // =====================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // =====================================================
    // Authentication Manager
    // =====================================================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {

        return authenticationConfiguration
                .getAuthenticationManager();
    }


    // =====================================================
    // Compromised Password Checker
    // =====================================================

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


    // =====================================================
    // Security Filter Chain
    // =====================================================

    @Bean
    public SecurityFilterChain customSecurityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {

        return httpSecurity

                // -------------------------------------------------
                // CSRF
                // -------------------------------------------------

                .csrf(csrf -> csrf.disable())


                // -------------------------------------------------
                // H2 Console
                // Development only
                // -------------------------------------------------

                .headers(header ->
                        header.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )


                // -------------------------------------------------
                // Authorization
                // -------------------------------------------------

                .authorizeHttpRequests(request ->
                        request

                                // ================================
                                // PUBLIC APIs
                                // ================================

                                .requestMatchers(
                                        "/auth/api/login",
                                        "/accounts/api",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()


                                // ================================
                                // H2 CONSOLE
                                // Development only
                                // ================================

                                .requestMatchers(
                                        "/h2-console/**"
                                ).permitAll()


                                // ================================
                                // CUSTOMER + ADMIN
                                // ================================

                                .requestMatchers(
                                        "/accounts/api/**",
                                        "/cards/api/**",
                                        "/loans/api/**",
                                        "/api/loans/payments/**",
                                        "/transactions/api/**"
                                ).hasAnyRole("CUSTOMER", "ADMIN")


                                // ================================
                                // EVERYTHING ELSE
                                // ================================

                                .anyRequest().authenticated()
                )


                // -------------------------------------------------
                // Disable Form Login
                // JWT based authentication
                // -------------------------------------------------

                .formLogin(form -> form.disable())


                // -------------------------------------------------
                // Disable HTTP Basic
                // JWT based authentication
                // -------------------------------------------------

                .httpBasic(config -> config.disable())


                // -------------------------------------------------
                // JWT Filter
                // -------------------------------------------------

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )


                .build();
    }
}