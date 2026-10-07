package com.rooptech.bankingapp.loan.entity;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.common.BaseEntity;
import com.rooptech.bankingapp.loan.constant.LoanStatus;
import com.rooptech.bankingapp.loan.constant.LoanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "loans")
@Entity
public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;
    @Column(nullable = false)
    private LocalDate loanIssuedAt;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal outstandingAmount;
    @Column(nullable = false)
    private Integer tenureMonths;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus loanStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanType loanType;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal interestRate;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal emi;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
