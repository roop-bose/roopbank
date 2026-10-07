package com.rooptech.bankingapp.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Customer details along with linked bank account information")
public class CustomerResponseDto {
    @Schema(
            description = "Unique identifier of the customer",
            example = "101"
    )
    private Long customerId;

    @Schema(
            description = "Full name of the customer",
            example = "Roop Bose"
    )
    private String customerName;

    @Schema(
            description = "Registered email address of the customer",
            example = "roop@example.com"
    )
    private String email;

    @Schema(
            description = "Registered 10-digit Indian mobile number",
            example = "9521567626"
    )
    private String mobileNumber;

    @Schema(
            description = "Linked bank account details"
    )
    private AccountResponseDto accountResponseDto;
}
