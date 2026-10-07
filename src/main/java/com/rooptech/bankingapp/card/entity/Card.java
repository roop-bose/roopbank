package com.rooptech.bankingapp.card.entity;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.card.constant.CardStatus;
import com.rooptech.bankingapp.card.constant.CardType;
import com.rooptech.bankingapp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long cardId;
    @Column(nullable = false,unique = true, length = 16)
   private String cardNumber;
    @Column(nullable = false)
   private LocalDate cardIssuedAt;
    @Column(nullable = false)
   private LocalDate expiryDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
   private CardStatus cardStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;
    @Column(nullable = false)
   private BigDecimal cardLimit;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "account_id", nullable = false)
   private Account account;


}
