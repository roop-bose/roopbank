package com.rooptech.bankingapp.transaction.dto;
import com.rooptech.bankingapp.transaction.constant.TransactionStatus;
import com.rooptech.bankingapp.transaction.constant.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Details of a bank account transaction")
public class TransactionResponseDto {
    @Schema(
            description = "Unique identifier of the transaction",
            example = "1001"
    )
    private Long transactionId;

    @Schema(
            description = "Amount involved in the transaction",
            example = "5000.00"
    )
    private BigDecimal amount;

    @Schema(
            description = "Account balance after the transaction",
            example = "25000.00"
    )
    private BigDecimal balanceAfter;

    @Schema(
            description = "Date and time when the transaction occurred",
            example = "2026-09-29T10:30:00"
    )
    private LocalDateTime transactionDate;

    @Schema(
            description = "Type of transaction",
            example = "DEPOSIT"
    )
    private TransactionType transactionType;

    @Schema(
            description = "Current status of the transaction",
            example = "SUCCESS"
    )
    private TransactionStatus transactionStatus;

    @Schema(
            description = "Unique identifier of the associated bank account",
            example = "1001"
    )
    private Long accountId;

}
