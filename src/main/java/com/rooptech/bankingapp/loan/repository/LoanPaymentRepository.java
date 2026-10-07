package com.rooptech.bankingapp.loan.repository;
import com.rooptech.bankingapp.loan.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {

    List<LoanPayment> findByLoanLoanIdOrderByPaymentDateDesc(
            Long loanId
    );
}
