package com.rooptech.bankingapp.account.service;
import com.rooptech.bankingapp.account.dto.CustomerRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerResponseDto;
import com.rooptech.bankingapp.account.dto.CustomerUpdateRequestDto;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.exception.CustomerAlreadyExistException;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.mapper.CustomerMapper;
import com.rooptech.bankingapp.account.repository.AccountRepository;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import com.rooptech.bankingapp.auth.entity.Role;
import com.rooptech.bankingapp.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void createAccount(CustomerRequestDto requestDto) {
          if (customerRepository.findByMobileNumber(requestDto.getMobileNumber()).isPresent()){
              throw  new CustomerAlreadyExistException("customer already exist with given mobile number");
          }
          Customer customer = CustomerMapper.toCustomer(requestDto, new Customer());
          Account newAccount = customer.getAccount();
          if (newAccount == null){
              throw  new IllegalArgumentException(
                      "Account information is required"
              );
          }
            long number = ThreadLocalRandom.current()
                      .nextLong(100_000_000_000L, 1_000_000_000_000L);
              newAccount.setAccountNumber(number);
              newAccount.setBalance(BigDecimal.ZERO);
        Role role = roleRepository.findByRoleName("ROLE_CUSTOMER")
                .orElseThrow(
                        ()->new RuntimeException("ROLE_CUSTOMER not found")
                );
        CompromisedPasswordDecision compromisedPasswordDecision=
                compromisedPasswordChecker.check(requestDto.getPassword());
        if (compromisedPasswordDecision.isCompromised()){
            throw new CompromisedPasswordException(
                    "Password has been compromised. Please choose another password.");
        }
        customer.setHashedPassword(passwordEncoder.encode(requestDto.getPassword()));
        customer.setRoles(Set.of(role));
              customerRepository.save(customer);

    }


    @Override
    public CustomerResponseDto getAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Customer", "mobileNumber",mobileNumber
                )
        );
     return CustomerMapper.toCustomerResponseDto(customer,
                new CustomerResponseDto());
    }

    @Override
    public void updateAccount(
            Long customerId,
            CustomerUpdateRequestDto requestDto) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer",
                                "customerId",
                                customerId.toString()
                        )
                );

        customer.setCustomerName(requestDto.getCustomerName());
        customer.setEmail(requestDto.getEmail());
        customer.setMobileNumber(requestDto.getMobileNumber());

        //No save() is required because you're using because we are using @Transactional
    }

    @Override
    public void deleteAccount(String mobileNumber) {
        Customer customer= customerRepository
                .findByMobileNumber(mobileNumber).orElseThrow(
                        ()-> new ResourceNotFoundException(
                                "Customer","mobileNumber",mobileNumber
                        )

                );

        // Added to remove customer-role mapping before deleting the customer
        customer.getRoles().clear();
        customerRepository.delete(customer);
    }

}
