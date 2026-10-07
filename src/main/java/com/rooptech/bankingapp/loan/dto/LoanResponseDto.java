package com.rooptech.bankingapp.loan.dto;

import com.rooptech.bankingapp.loan.constant.LoanStatus;
import com.rooptech.bankingapp.loan.constant.LoanType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
@Schema(description = "Loan details associated with a bank account")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoanResponseDto {

    @Schema(
            description = "Unique identifier of the loan",
            example = "501"
    )
    private Long loanId;

    @Schema(
            description = "Original amount of the loan",
            example = "500000.00"
    )
    private BigDecimal loanAmount;

    @Schema(
            description = "Date when the loan was issued",
            example = "2026-09-29"
    )
    private LocalDate loanIssuedAt;

    @Schema(
            description = "Current outstanding amount of the loan",
            example = "500000.00"
    )
    private BigDecimal outstandingAmount;

    @Schema(
            description = "Loan repayment tenure in months",
            example = "60"
    )
    private Integer tenureMonths;

    @Schema(
            description = "Current status of the loan",
            example = "ACTIVE"
    )
    private LoanStatus loanStatus;

    @Schema(
            description = "Type of the loan",
            example = "PERSONAL"
    )
    private LoanType loanType;

    @Schema(
            description = "Annual interest rate applicable to the loan",
            example = "10.50"
    )
    private BigDecimal interestRate;

    @Schema(
            description = "Monthly EMI amount",
            example = "10747.00"
    )
    private BigDecimal emi;

    @Schema(
            description = "ID of the bank account associated with the loan",
            example = "1001"
    )
    private Long accountId;
}
