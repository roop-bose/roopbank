package com.rooptech.bankingapp.card.service;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.repository.AccountRepository;
import com.rooptech.bankingapp.card.constant.CardStatus;
import com.rooptech.bankingapp.card.dto.CardRequestDto;
import com.rooptech.bankingapp.card.dto.CardResponseDto;
import com.rooptech.bankingapp.card.dto.CardUpdateRequestDto;
import com.rooptech.bankingapp.card.entity.Card;
import com.rooptech.bankingapp.card.exception.CardAlreadyExistsException;
import com.rooptech.bankingapp.card.mapper.CardMapper;
import com.rooptech.bankingapp.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements  CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    @Override
    public void createCard(CardRequestDto cardRequestDto) {
         Account account = accountRepository.findById(cardRequestDto.getAccountId()).orElseThrow(
                 ()-> new ResourceNotFoundException("Account","accountId"
                         ,cardRequestDto.getAccountId().toString())
         );
         if (cardRepository.existsByAccountAccountIdAndCardType(cardRequestDto.getAccountId(),
                    cardRequestDto.getCardType()
                 )){
             throw  new CardAlreadyExistsException("A " +
                     cardRequestDto.getCardType()
                     + " card already exists for this account");
         }
             Card card = CardMapper.toCard(cardRequestDto, new Card());
             String cardNumber;
             do {
                 cardNumber = String.valueOf(
                         ThreadLocalRandom.current()
                                 .nextLong(
                                         1_000_000_000_000_000L,
                                         10_000_000_000_000_000L
                                 )
                 );
             } while (cardRepository.existsByCardNumber(cardNumber));
           LocalDate issuedAt = LocalDate.now();
             card.setCardNumber(cardNumber);
             card.setCardStatus(CardStatus.ACTIVE);
             card.setCardLimit(BigDecimal.valueOf(10000));
             card.setCardIssuedAt(issuedAt);
             card.setAccount(account);
             card.setExpiryDate(issuedAt.plusYears(3));
             cardRepository.save(card);


    }
    @Override
    public CardResponseDto getCard(Long accountId,Long cardId ) {
           Card card= cardRepository.findByCardIdAndAccountAccountId( cardId,accountId).orElseThrow(
                   ()-> new ResourceNotFoundException(
                           "Card","accountId and cardId ",cardId.toString()
                   )

           );

        return CardMapper.toCardResponseDto(card, new CardResponseDto());
    }
    @Override
    public void updateCard(Long accountId,
                           Long cardId, CardUpdateRequestDto cardRequest) {
        Card card= cardRepository.findByCardIdAndAccountAccountId(cardId,accountId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Card","accountId and cardId ",cardId.toString()
                )
                );


          card.setCardStatus(cardRequest.getCardStatus());
          card.setCardLimit(cardRequest.getCardLimit());
        cardRepository.save(card);

    }
    @Override
    public void deleteCard(Long accountId, Long cardId) {
        Card card= cardRepository.findByCardIdAndAccountAccountId(cardId,accountId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Card","accountId and cardId ",cardId.toString()
                )

        );
        cardRepository.delete(card);

    }
}
