package com.rooptech.bankingapp.loan.service;
import com.rooptech.bankingapp.loan.dto.LoanPaymentRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanPaymentResponseDto;
import com.rooptech.bankingapp.loan.entity.LoanPayment;

import java.util.List;

public interface LoanPaymentService {
    LoanPaymentResponseDto makePayment(LoanPaymentRequestDto loanPaymentRequestDto);
    List<LoanPaymentResponseDto> getPaymentHistory(
            Long loanId
    );
}
