package com.rooptech.bankingapp.loan.mapper;

import com.rooptech.bankingapp.loan.dto.LoanPaymentResponseDto;
import com.rooptech.bankingapp.loan.entity.LoanPayment;

public class LoanPaymentMapper {
    public static LoanPaymentResponseDto responseDto(
            LoanPayment loanPayment
    ) {

        return LoanPaymentResponseDto.builder()
                .paymentId(loanPayment.getPaymentId())
                .loanId(loanPayment.getLoan().getLoanId())
                .paymentAmount(loanPayment.getPaymentAmount())
                .principalAmount(loanPayment.getPrincipalAmount())
                .interestAmount(loanPayment.getInterestAmount())
                .paymentDate(loanPayment.getPaymentDate())
                .paymentStatus(loanPayment.getPaymentStatus())
                .interestRate(loanPayment.getMonthlyInterest())
                .outstandingAmount(
                        loanPayment.getOutstandingAmount()
                )
                .build();
    }
}
