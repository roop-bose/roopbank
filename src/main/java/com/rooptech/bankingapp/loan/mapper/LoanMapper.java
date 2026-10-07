package com.rooptech.bankingapp.loan.mapper;
import com.rooptech.bankingapp.loan.dto.LoanRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanResponseDto;
import com.rooptech.bankingapp.loan.entity.Loan;

public class LoanMapper {
    public static Loan toLoanEntity(
            LoanRequestDto loanRequestDto,
            Loan loan) {

        loan.setLoanAmount(loanRequestDto.getLoanAmount());
        loan.setTenureMonths(loanRequestDto.getTenureMonths());
        loan.setLoanType(loanRequestDto.getLoanType());

        return loan;
    }

    public static LoanResponseDto responseDto(
            Loan loan,
            LoanResponseDto loanResponseDto) {

        loanResponseDto.setLoanId(loan.getLoanId());
        loanResponseDto.setLoanAmount(loan.getLoanAmount());
        loanResponseDto.setLoanIssuedAt(loan.getLoanIssuedAt());
        loanResponseDto.setOutstandingAmount(loan.getOutstandingAmount());
        loanResponseDto.setTenureMonths(loan.getTenureMonths());
        loanResponseDto.setLoanStatus(loan.getLoanStatus());
        loanResponseDto.setLoanType(loan.getLoanType());
        loanResponseDto.setInterestRate(loan.getInterestRate());
        loanResponseDto.setEmi(loan.getEmi());

        if (loan.getAccount() != null) {
            loanResponseDto.setAccountId(
                    loan.getAccount().getAccountId()
            );
        }

        return loanResponseDto;
    }
}
