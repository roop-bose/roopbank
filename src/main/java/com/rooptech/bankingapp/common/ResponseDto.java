package com.rooptech.bankingapp.common;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(description = "Standard response returned after a successful API operation")
public class ResponseDto {
    @Schema(
            description = "HTTP status of the operation",
            example = "CREATED"
    )
    private HttpStatus statusCode;

    @Schema(
            description = "Message describing the result of the operation",
            example = "account created successfully"
    )
    private String statusMessage;
}
