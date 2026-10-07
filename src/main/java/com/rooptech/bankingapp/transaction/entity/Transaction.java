package com.rooptech.bankingapp.transaction.entity;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.transaction.constant.TransactionStatus;
import com.rooptech.bankingapp.transaction.constant.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(nullable = false, precision = 15,scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, precision = 15,scale = 2)
    private BigDecimal balanceAfter;
    @Column(nullable = false)
    private LocalDateTime transactionDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

}
