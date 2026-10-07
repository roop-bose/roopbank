package com.rooptech.bankingapp.card.dto;

import com.rooptech.bankingapp.card.constant.CardStatus;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardUpdateRequestDto {

    private CardStatus cardStatus;

    @Positive(message = "Card limit must be positive")
    private BigDecimal cardLimit;
}