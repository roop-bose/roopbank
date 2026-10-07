package com.rooptech.bankingapp.account.entity;
import com.rooptech.bankingapp.account.constant.AccountType;
import com.rooptech.bankingapp.card.entity.Card;
import com.rooptech.bankingapp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter  @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(nullable = false,length = 200)
    private String branch;
    @Column(nullable = false,length = 15)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(nullable = false,unique = true)
    private Long accountNumber;
    @Column(nullable = false)
    private BigDecimal balance;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
}
