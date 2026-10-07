package com.rooptech.bankingapp.card.dto;

import com.rooptech.bankingapp.card.constant.CardType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor @AllArgsConstructor
public class CardRequestDto {
    @NotNull(message = "Account ID is required")
    @Positive(message = "Account ID must be positive")
    private Long accountId;
    @NotNull(message = "Card type is required")
    private CardType cardType;
}
