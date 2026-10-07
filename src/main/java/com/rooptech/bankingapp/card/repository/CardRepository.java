package com.rooptech.bankingapp.card.repository;
import com.rooptech.bankingapp.card.constant.CardType;
import com.rooptech.bankingapp.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CardRepository extends JpaRepository<Card,Long> {
    Optional<Card> findByCardIdAndAccountAccountId( Long cardId,
                                                    Long accountId);
    boolean existsByCardNumber(String cardNumber);
    boolean existsByAccountAccountIdAndCardType(Long accountId, CardType cardType);
}
