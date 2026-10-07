package com.rooptech.bankingapp.auth.contoller;

import com.rooptech.bankingapp.auth.dto.LoginRequestDto;
import com.rooptech.bankingapp.auth.dto.LoginResponseDto;
import com.rooptech.bankingapp.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/auth/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "Authentication",
        description = "APIs for customer authentication and JWT token generation"
)
public class AuthController {
 private final AuthService authService;
    @Operation(
            summary = "Customer login",
            description = """
                Authenticates a customer using email and password.
                If the credentials are valid, the API generates and
                returns a JWT token that can be used to access
                protected APIs.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer authenticated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Login Success",
                                    value = """
                                        {
                                          "message": "logged in successfully",
                                          "token": "eyJhbGciOiJIUzI1NiJ9..."
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid login request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "email": "email must be valid",
                                          "password": "password cannot be blank"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Credentials",
                                    value = """
                                        {
                                          "error": "Unauthorized"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found with the given email",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.rooptech.bankingapp.common.ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Customer Not Found",
                                    value = """
                                        {
                                          "apiPath": "/auth/api/login",
                                          "errorTime": "2026-09-29T17:00:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Customer not found with given email"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = com.rooptech.bankingapp.common.ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Internal Server Error",
                                    value = """
                                        {
                                          "apiPath": "/auth/api/login",
                                          "errorTime": "2026-09-29T17:00:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid
            @RequestBody
            LoginRequestDto loginRequestDto
    ){

        return ResponseEntity.ok(authService.customerLogin(loginRequestDto));
    }

}
