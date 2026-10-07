package com.rooptech.bankingapp.loan.service;

import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.repository.AccountRepository;
import com.rooptech.bankingapp.loan.calculator.LoanCalculator;
import com.rooptech.bankingapp.loan.constant.LoanStatus;
import com.rooptech.bankingapp.loan.constant.LoanType;
import com.rooptech.bankingapp.loan.dto.LoanRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanResponseDto;
import com.rooptech.bankingapp.loan.entity.Loan;
import com.rooptech.bankingapp.loan.entity.LoanProduct;
import com.rooptech.bankingapp.loan.exception.LoanAlreadyExistsException;
import com.rooptech.bankingapp.loan.mapper.LoanMapper;
import com.rooptech.bankingapp.loan.repository.LoanProductRepository;
import com.rooptech.bankingapp.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {
    private final AccountRepository accountRepository;
    private final LoanRepository loanRepository;
    private final LoanProductRepository loanProductRepository;

    @Override
    public void createLoan(LoanRequestDto loanRequestDto) {
        Account account = accountRepository.findById(loanRequestDto.getAccountId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Account",
                        "accountId",
                        loanRequestDto.getAccountId().toString())
        );

        if (loanRepository.existsByAccountAccountIdAndLoanType(
                loanRequestDto.getAccountId(), loanRequestDto.getLoanType()
        )) {
            throw new LoanAlreadyExistsException("Loan already exists with account "
                    + loanRequestDto.getAccountId()
                    + " and loan type "
                    + loanRequestDto.getLoanType());
        }


        LoanProduct loanProduct =
                loanProductRepository.findByLoanTypeAndActiveTrue(loanRequestDto.getLoanType()).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "LoanProduct",
                                "loanType",
                                loanRequestDto.getLoanType().name()
                        )
                );
        if (loanRequestDto.getLoanAmount()
                .compareTo(loanProduct.getMaxLoanAmount()) > 0) {

            throw new IllegalArgumentException(
                    "Loan amount exceeds maximum allowed amount for "
                            + loanRequestDto.getLoanType()
            );
        }
        if (loanRequestDto.getTenureMonths()
                > loanProduct.getMaxTenureMonths()) {

            throw new IllegalArgumentException(
                    "Loan tenure exceeds maximum allowed tenure for "
                            + loanRequestDto.getLoanType()
            );
        }
        Loan loan = LoanMapper.toLoanEntity(loanRequestDto, new Loan());
        BigDecimal interestRate = loanProduct.getInterestRate();
        BigDecimal emi = LoanCalculator.emiCalculator(
                loanRequestDto.getLoanAmount(),
                interestRate,
                loanRequestDto.getTenureMonths()
        );
        loan.setLoanStatus(LoanStatus.ACTIVE);
        loan.setLoanIssuedAt(LocalDate.now());
        loan.setOutstandingAmount(loan.getLoanAmount());
        loan.setInterestRate(interestRate);
        loan.setEmi(emi);
        loan.setAccount(account);
        loanRepository.save(loan);
    }

    @Override
    public LoanResponseDto getLoan(Long accountId, LoanType loanType) {
        Loan loan = loanRepository.findByAccountAccountIdAndLoanType(accountId, loanType).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Loan",
                        "loanType and accountId",
                        loanType.name() + " " + accountId
                )
        );

        return LoanMapper.responseDto(loan, new LoanResponseDto());
    }


    @Override
    public void deleteLoan(Long accountId, LoanType loanType) {
        Loan loan = loanRepository.findByAccountAccountIdAndLoanType(accountId, loanType).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Loan",
                        "loanType and accountId",
                        loanType.name() + " " + accountId
                ));
        loanRepository.delete(loan);
    }
}
