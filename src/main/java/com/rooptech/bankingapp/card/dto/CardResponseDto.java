package com.rooptech.bankingapp.card.dto;

import com.rooptech.bankingapp.card.constant.CardStatus;
import com.rooptech.bankingapp.card.constant.CardType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Card details associated with a bank account")
public class CardResponseDto {
    @Schema(
            description = "Unique identifier of the card",
            example = "101"
    )
    private Long cardId;
    @Schema(
            description = "Unique 16-digit card number",
            example = "1234567890123456"
    )

    private String cardNumber;
    @Schema(
            description = "Date when the card was issued",
            example = "2026-09-28"
    )
    private LocalDate cardIssuedAt;
    @Schema(
            description = "Date when the card expires",
            example = "2029-09-28"
    )
    private LocalDate expiryDate;
    @Schema(
            description = "Current status of the card",
            example = "ACTIVE"
    )
    private CardStatus cardStatus;
    @Schema(
            description = "Maximum spending limit assigned to the card",
            example = "10000.00"
    )
    private BigDecimal cardLimit;

    @Schema(
            description = "Type of the card",
            example = "CREDIT"
    )
    private CardType cardType;

    @Schema(
            description = "ID of the bank account linked to the card",
            example = "1001"
    )
    private Long accountId;

}
