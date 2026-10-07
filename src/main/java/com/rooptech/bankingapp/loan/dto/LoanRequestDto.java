package com.rooptech.bankingapp.loan.dto;
import com.rooptech.bankingapp.loan.constant.LoanType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoanRequestDto {

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be greater than zero")
    private BigDecimal loanAmount;

    @NotNull(message = "Tenure months is required")
    @Positive(message = "Tenure months must be greater than zero")
    private Integer tenureMonths;

    @NotNull(message = "Loan type is required")
    private LoanType loanType;


    @NotNull(message = "Account ID is required")
    @Positive(message = "Account ID must be greater than zero")
    private Long accountId;
}
