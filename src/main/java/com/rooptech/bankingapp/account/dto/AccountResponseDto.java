package com.rooptech.bankingapp.account.dto;
import com.rooptech.bankingapp.account.constant.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@AllArgsConstructor @NoArgsConstructor
@Schema(description = "Bank account details linked to a customer")
public class AccountResponseDto {
    @Schema(
            description = "Branch where the account is maintained",
            example = "Mumbai Main Branch"
    )
    private String branch;

    @Schema(
            description = "Type of the bank account",
            example = "SAVINGS"
    )
    private AccountType accountType;

    @Schema(
            description = "Unique bank account number",
            example = "123456789012"
    )
    private Long accountNumber;

    @Schema(
            description = "Current available account balance",
            example = "25000.50"
    )
    private BigDecimal balance;
}
