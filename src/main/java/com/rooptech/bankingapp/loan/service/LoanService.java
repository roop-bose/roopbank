package com.rooptech.bankingapp.loan.service;
import com.rooptech.bankingapp.loan.constant.LoanType;
import com.rooptech.bankingapp.loan.dto.LoanRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanResponseDto;

public interface LoanService {
    void createLoan(LoanRequestDto loanRequestDto);
    LoanResponseDto getLoan(Long accountId , LoanType loanType);
//    void updateLoan(LoanRequestDto loanRequestDto);  no update for our business requirement
    void deleteLoan(Long accountId,LoanType loanType);
}
