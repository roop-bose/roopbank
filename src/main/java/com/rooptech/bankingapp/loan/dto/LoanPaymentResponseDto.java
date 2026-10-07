package com.rooptech.bankingapp.loan.dto;
import com.rooptech.bankingapp.loan.constant.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Schema(description = "Details of a loan payment")
public class LoanPaymentResponseDto {
    @Schema(
            description = "Unique identifier of the payment",
            example = "1001"
    )
    private Long paymentId;

    @Schema(
            description = "Total amount paid towards the loan",
            example = "10747.00"
    )
    private BigDecimal paymentAmount;

    @Schema(
            description = "Annual interest rate applicable to the loan",
            example = "10.50"
    )
    private BigDecimal interestRate;

    @Schema(
            description = "Amount of the payment applied towards loan principal",
            example = "6372.00"
    )
    private BigDecimal principalAmount;

    @Schema(
            description = "Interest portion of the payment",
            example = "4375.00"
    )
    private BigDecimal interestAmount;

    @Schema(
            description = "Date on which the payment was made",
            example = "2026-09-29"
    )
    private LocalDate paymentDate;

    @Schema(
            description = "Remaining outstanding loan amount after the payment",
            example = "493628.00"
    )
    private BigDecimal outstandingAmount;

    @Schema(
            description = "Current status of the payment",
            example = "SUCCESS"
    )
    private PaymentStatus paymentStatus;

    @Schema(
            description = "Unique identifier of the associated loan",
            example = "501"
    )
    private Long loanId;
}
