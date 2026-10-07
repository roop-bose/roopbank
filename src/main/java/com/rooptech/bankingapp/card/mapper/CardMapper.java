package com.rooptech.bankingapp.card.mapper;

import com.rooptech.bankingapp.card.dto.CardRequestDto;
import com.rooptech.bankingapp.card.dto.CardResponseDto;
import com.rooptech.bankingapp.card.entity.Card;

public class CardMapper {

    public static Card toCard(CardRequestDto cardRequestDto,
                              Card card
                              ){
        card.setCardType(cardRequestDto.getCardType());
        return card;
    }

    public static CardResponseDto toCardResponseDto(Card card,CardResponseDto cardResponseDto){
           cardResponseDto.setCardId(card.getCardId());
           cardResponseDto.setCardNumber(card.getCardNumber());
           cardResponseDto.setCardStatus(card.getCardStatus());
           cardResponseDto.setCardType(card.getCardType());
           cardResponseDto.setCardLimit(card.getCardLimit());
           cardResponseDto.setCardIssuedAt(card.getCardIssuedAt());
           cardResponseDto.setExpiryDate(card.getExpiryDate());
           if (card.getAccount()!=null){
               cardResponseDto.setAccountId(card.getAccount().getAccountId());
           }
      return cardResponseDto;
    }
}
