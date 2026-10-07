package com.rooptech.bankingapp.loan.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanPaymentRequestDto {
    @NotNull(message = "Loan ID is required")
    private Long loanId;


}
