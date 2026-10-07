package com.rooptech.bankingapp.account.service;
import com.rooptech.bankingapp.account.dto.CustomerRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerResponseDto;
import com.rooptech.bankingapp.account.dto.CustomerUpdateRequestDto;

public interface AccountService {
    void  createAccount(CustomerRequestDto requestDto);
    CustomerResponseDto getAccount(String mobileNumber);
    void updateAccount(Long customerId, CustomerUpdateRequestDto requestDto);
    void deleteAccount(String mobileNumber);

}
