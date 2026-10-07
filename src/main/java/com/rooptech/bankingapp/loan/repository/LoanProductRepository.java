package com.rooptech.bankingapp.loan.repository;
import com.rooptech.bankingapp.loan.constant.LoanType;
import com.rooptech.bankingapp.loan.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {

    Optional<LoanProduct> findByLoanTypeAndActiveTrue(
            LoanType loanType
    );
}
