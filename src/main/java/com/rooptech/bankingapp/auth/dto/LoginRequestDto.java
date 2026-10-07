package com.rooptech.bankingapp.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Customer login credentials")
public class LoginRequestDto {

    @Schema(
            description = "Registered email address of the customer",
            example = "roop@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    @Schema(
            description = "Customer account password",
            example = "Password@123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "password cannot be blank")
    private String password;

//    @NotBlank(message = "email cannot be blank")
//    @Email(message = "email must be valid")
//    private String email;
//    @NotBlank(message = "password cannot be blank")
//    private String password;
}