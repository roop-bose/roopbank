package com.rooptech.bankingapp.account.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor @AllArgsConstructor
public class CustomerRequestDto {
    @NotBlank(message = "customer name is required")
    @Size(min = 3, max = 70, message = "Customer name must be between 3 and 50 characters")
    private String customerName;
    @NotBlank(message =  "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Mobile number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
             message = "Mobile number must be a valid 10-digit Indian mobile number"
    )
    private String mobileNumber;

    @NotBlank(message = "password cannot be blank")
    @Size(
            min = 8,
            message = "password must be at least 8 characters"
    )
    private String password;
    @NotNull(message = "Account details are required")
    @Valid
    private AccountRequestDto accountRequestDto;
}
