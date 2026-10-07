package com.rooptech.bankingapp.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Request containing the transaction amount")
public class TransactionRequestDto {
    @Schema(
            description = "Amount to deposit or withdraw",
            example = "5000.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "amount can not be null")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;
}
