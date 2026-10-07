package com.rooptech.bankingapp.account.dto;
import com.rooptech.bankingapp.account.constant.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor @NoArgsConstructor
public class AccountRequestDto {
    @NotBlank(message = "Branch is required")
    @Size(min = 3, max = 100, message = "Branch must be between 3 and 100 characters")
    private String branch;
    @NotNull(message = "Account type is required")
    private AccountType accountType;
}
