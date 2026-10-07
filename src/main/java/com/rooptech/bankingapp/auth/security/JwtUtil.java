package com.rooptech.bankingapp.auth.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    //private final long jwtExpiration = 86400000L; // 24 hours
   // private final long jwtExpiration = 600_000L; // 10 minutes
    private final long jwtExpiration = 3_600_000L;

    // =========================================================
    // Generate JWT
    // =========================================================

    public String generateToken(Authentication authentication) {

        return Jwts.builder()

                .issuer("banking-app")

                .subject(authentication.getName())

                .claim(
                        "role",
                        authentication.getAuthorities()
                                .stream()
                                .findFirst()
                                .map(authority ->
                                        authority.getAuthority()
                                )
                                .orElse(null)
                )

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + jwtExpiration
                        )
                )

                .signWith(getSecretKey())

                .compact();
    }


    // =========================================================
    // Secret Key
    // =========================================================

    public SecretKey getSecretKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    // =========================================================
    // Extract Username
    // =========================================================

    public String extractUsername(String jwt) {

        Claims claims = getClaims(jwt);

        return claims.getSubject();
    }


    // =========================================================
    // Extract Role
    // =========================================================

    public String extractRole(String jwt) {

        Claims claims = getClaims(jwt);

        return claims.get(
                "role",
                String.class
        );
    }


    // =========================================================
    // Validate Token
    // =========================================================

    public boolean validateToken(String jwt) {

        try {

            getClaims(jwt);

            return true;

        } catch (Exception ex) {

            return false;
        }
    }


    // =========================================================
    // Parse + Verify JWT
    // =========================================================

    private Claims getClaims(String jwt) {

        return Jwts.parser()

                .verifyWith(getSecretKey())

                .build()

                .parseSignedClaims(jwt)

                .getPayload();
    }
}