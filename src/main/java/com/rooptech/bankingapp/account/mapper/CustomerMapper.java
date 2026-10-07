package com.rooptech.bankingapp.account.mapper;
import com.rooptech.bankingapp.account.dto.AccountResponseDto;
import com.rooptech.bankingapp.account.dto.CustomerRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerResponseDto;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.entity.Customer;
public class CustomerMapper {
    public static Customer toCustomer(CustomerRequestDto customerRequestDto,
                                      Customer customer){
        customer.setCustomerName(customerRequestDto.getCustomerName());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setMobileNumber(customerRequestDto.getMobileNumber());
        if (customerRequestDto.getAccountRequestDto()!=null){
            if (customer.getAccount() !=null){
               Account account= AccountMapper.toAccount(customerRequestDto.getAccountRequestDto(),
                        customer.getAccount());

            }else {
                Account account=
                    AccountMapper.toAccount(customerRequestDto.getAccountRequestDto()
                            ,new Account());
                customer.setAccount(account);
            }}

        return customer;
    }
    public static CustomerResponseDto toCustomerResponseDto(Customer customer,
                                                            CustomerResponseDto customerResponseDto){
        customerResponseDto.setCustomerId(customer.getCustomerId());
        customerResponseDto.setCustomerName(customer.getCustomerName());
        customerResponseDto.setMobileNumber(customer.getMobileNumber());
        customerResponseDto.setEmail(customer.getEmail());
        if (customer.getAccount()!=null){
              AccountResponseDto accountResponseDto=
                      AccountMapper.toAccountResponseDto(customer.getAccount(),
                           new AccountResponseDto());
              customerResponseDto.setAccountResponseDto(accountResponseDto);
        }
        return customerResponseDto;
    }
}
