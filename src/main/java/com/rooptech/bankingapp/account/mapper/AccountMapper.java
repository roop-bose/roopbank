package com.rooptech.bankingapp.account.mapper;
import com.rooptech.bankingapp.account.dto.AccountRequestDto;
import com.rooptech.bankingapp.account.dto.AccountResponseDto;
import com.rooptech.bankingapp.account.entity.Account;

public class AccountMapper {

    public static Account toAccount(AccountRequestDto accountRequestDto,
                                    Account account){
        account.setBranch(accountRequestDto.getBranch());
        account.setAccountType(accountRequestDto.getAccountType());
        return account;
    }

    public static AccountResponseDto toAccountResponseDto(Account account,
                                                          AccountResponseDto accountResponseDto){
        accountResponseDto.setBranch(account.getBranch());
        accountResponseDto.setAccountNumber(account.getAccountNumber());
        accountResponseDto.setAccountType(account.getAccountType());
        accountResponseDto.setBalance(account.getBalance());
        return accountResponseDto;
    }

}
