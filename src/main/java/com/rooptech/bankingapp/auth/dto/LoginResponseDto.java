package com.rooptech.bankingapp.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response returned after successful customer authentication")
public class LoginResponseDto {
    @Schema(
            description = "Message indicating the authentication result",
            example = "logged in successfully"
    )
    private String message;

    @Schema(
            description = "JWT access token used to authenticate requests to protected APIs",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String token;


//    private String message;
//    private String token;
}
