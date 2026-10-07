package com.rooptech.bankingapp.card.service;
import com.rooptech.bankingapp.card.dto.CardRequestDto;
import com.rooptech.bankingapp.card.dto.CardResponseDto;
import com.rooptech.bankingapp.card.dto.CardUpdateRequestDto;
import com.rooptech.bankingapp.card.entity.Card;
public interface CardService {
    void createCard(CardRequestDto cardRequestDto);
    CardResponseDto getCard(Long accountId, Long cardId);
    void updateCard(Long accountId, Long cardId, CardUpdateRequestDto cardUpdateRequestDto);
    void deleteCard(Long accountId, Long cardId);
}
