package com.rooptech.bankingapp.loan.service;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.loan.calculator.LoanPaymentCalculator;
import com.rooptech.bankingapp.loan.constant.LoanStatus;
import com.rooptech.bankingapp.loan.constant.PaymentStatus;
import com.rooptech.bankingapp.loan.dto.LoanPaymentRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanPaymentResponseDto;
import com.rooptech.bankingapp.loan.entity.Loan;
import com.rooptech.bankingapp.loan.entity.LoanPayment;
import com.rooptech.bankingapp.loan.mapper.LoanPaymentMapper;
import com.rooptech.bankingapp.loan.repository.LoanPaymentRepository;
import com.rooptech.bankingapp.loan.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanPaymentServiceImpl implements LoanPaymentService {
    private final LoanRepository loanRepository;
    private final LoanPaymentRepository loanPaymentRepository;

    @Override
    @Transactional
    public LoanPaymentResponseDto makePayment(
            LoanPaymentRequestDto requestDto
    ) {

        // 1. Find loan
        Loan loan = loanRepository.findById(requestDto.getLoanId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "loanId",
                                requestDto.getLoanId().toString()
                        )
                );

        // 2. Check loan status
        if (loan.getLoanStatus() != LoanStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Loan is not active"
            );
        }

        // 3. Get account associated with loan
        Account account = loan.getAccount();

        // 4. Payment amount = EMI
        BigDecimal emi = loan.getEmi();

        // 5. For final payment, don't pay more than outstanding
        BigDecimal paymentAmount = emi.min(
                loan.getOutstandingAmount()
        );

        // 6. Check account balance
        if (account.getBalance().compareTo(paymentAmount) < 0) {
            throw new IllegalStateException(
                    "Insufficient account balance"
            );
        }

        // 7. Calculate interest
        BigDecimal interestAmount =
                LoanPaymentCalculator.calculateMonthlyInterestAmount(
                        loan.getOutstandingAmount(),
                        loan.getInterestRate()
                );

        // 8. Calculate principal
        BigDecimal principalAmount =
                paymentAmount.subtract(interestAmount);

        // 9. Calculate new outstanding
        BigDecimal newOutstanding =
                LoanPaymentCalculator.calculateOutstanding(
                        loan.getOutstandingAmount(),
                        principalAmount
                );

        // 10. Deduct payment from account
        account.setBalance(
                account.getBalance().subtract(paymentAmount)
        );

        // 11. Update loan
        loan.setOutstandingAmount(newOutstanding);

        if (newOutstanding.compareTo(BigDecimal.ZERO) == 0) {
            loan.setLoanStatus(LoanStatus.CLOSED);
        }

        // 12. Create payment record
        LoanPayment loanPayment = new LoanPayment();
        loanPayment.setOutstandingAmount(newOutstanding);
        loanPayment.setMonthlyInterest(loan.getInterestRate());
        loanPayment.setPaymentAmount(paymentAmount);
        loanPayment.setPrincipalAmount(principalAmount);
        loanPayment.setInterestAmount(interestAmount);
        loanPayment.setPaymentDate(LocalDate.now());
        loanPayment.setPaymentStatus(PaymentStatus.SUCCESS);
        loanPayment.setLoan(loan);

        // 13. Save payment
        LoanPayment savedPayment =
                loanPaymentRepository.save(loanPayment);

        // 14. Return response
        return LoanPaymentMapper.responseDto(savedPayment);
    }
    @Override
    @Transactional
    public List<LoanPaymentResponseDto> getPaymentHistory(
            Long loanId
    ) {

        // First verify that loan exists
        loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "loanId",
                                loanId.toString()
                        )
                );

        List<LoanPayment> payments =
                loanPaymentRepository
                        .findByLoanLoanIdOrderByPaymentDateDesc(loanId);

        return payments.stream()
                .map(LoanPaymentMapper::responseDto)
                .toList();
    }
}
