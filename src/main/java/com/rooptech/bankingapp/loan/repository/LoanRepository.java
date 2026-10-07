package com.rooptech.bankingapp.loan.repository;
import com.rooptech.bankingapp.loan.constant.LoanType;
import com.rooptech.bankingapp.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByAccountAccountIdAndLoanType(Long accountId, LoanType loanType);
    boolean existsByAccountAccountIdAndLoanType(Long accountId, LoanType loanType);
}
