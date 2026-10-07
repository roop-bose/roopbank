package com.rooptech.bankingapp.common;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Schema(description = "Standard error response returned when an API request fails")
public class ErrorResponseDto {
    @Schema(
            description = "API path where the error occurred",
            example = "/accounts/api/9521567626"
    )
    private String apiPath;

    @Schema(
            description = "Date and time when the error occurred",
            example = "2026-09-25T18:30:00"
    )
    private LocalDateTime errorTime;

    @Schema(
            description = "HTTP error code",
            example = "404 NOT_FOUND"
    )
    private String errorCode;

    @Schema(
            description = "Detailed error message",
            example = "Customer not found with mobileNumber : 9521567626"
    )
    private String errorMessage;

}
