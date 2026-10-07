package com.rooptech.bankingapp.loan.entity;
import com.rooptech.bankingapp.common.BaseEntity;
import com.rooptech.bankingapp.loan.constant.LoanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "loan_products")
public class LoanProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanProductId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private LoanType loanType;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private Integer maxTenureMonths;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal maxLoanAmount;

    @Column(nullable = false)
    private Boolean active;

}
