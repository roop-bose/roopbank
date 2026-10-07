package com.rooptech.bankingapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RoopBank API",
                version = "1.0.0",
                description ="""
        RoopBank is a RESTful banking application that provides APIs
        for managing customer accounts, cards, transactions, loans,
        loan payments and authentication.
        """,
                contact = @Contact(
                        name = "Roop - RoopTech",
                        email = "ruparambose@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
