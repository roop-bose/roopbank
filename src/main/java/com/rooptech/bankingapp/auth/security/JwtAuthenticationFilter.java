package com.rooptech.bankingapp.auth.security;

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

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        // =====================================================
        // 1. Get Authorization header
        // =====================================================

        String authHeader =
                request.getHeader("Authorization");


        // =====================================================
        // 2. Check Bearer token
        // =====================================================

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }


        // =====================================================
        // 3. Extract JWT
        // =====================================================

        String jwt = authHeader.substring(7);


        // =====================================================
        // 4. Validate JWT
        // =====================================================

        if (jwtUtil.validateToken(jwt)
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {


            // =================================================
            // 5. Extract username from JWT
            // =================================================

            String username =
                    jwtUtil.extractUsername(jwt);


            // =================================================
            // 6. Load user from database
            // =================================================

            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(username);


            // =================================================
            // 7. Create Authentication object
            // =================================================

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );


            // =================================================
            // 8. Attach request details
            // =================================================

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );


            // =================================================
            // 9. Store Authentication in SecurityContext
            // =================================================

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }


        // =====================================================
        // 10. Continue filter chain
        // =====================================================

        filterChain.doFilter(request, response);
    }
}