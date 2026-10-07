package com.rooptech.bankingapp.account.service;

import com.rooptech.bankingapp.account.constant.AccountType;
import com.rooptech.bankingapp.account.dto.AccountRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerResponseDto;
import com.rooptech.bankingapp.account.dto.CustomerUpdateRequestDto;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.exception.CustomerAlreadyExistException;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.repository.AccountRepository;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import com.rooptech.bankingapp.auth.entity.Role;
import com.rooptech.bankingapp.auth.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void shouldCreateAccountSuccessfully() {

        // Arrange

        AccountRequestDto accountRequestDto =
                new AccountRequestDto(
                        "Mumbai",
                        AccountType.SAVING
                );

        CustomerRequestDto requestDto =
                new CustomerRequestDto(
                        "Rahul Sharma",
                        "rahul@gmail.com",
                        "9876543210",
                        "password123",
                        accountRequestDto
                );

        Role role = new Role();
        role.setRoleName("ROLE_CUSTOMER");

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());

        when(roleRepository.findByRoleName("ROLE_CUSTOMER"))
                .thenReturn(Optional.of(role));

        when(compromisedPasswordChecker.check("password123"))
                .thenReturn(
                        new CompromisedPasswordDecision(false)
                );

        when(passwordEncoder.encode("password123"))
                .thenReturn("encoded-password");


        // Act

        accountService.createAccount(requestDto);


        // Assert

        ArgumentCaptor<Customer> customerCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerRepository)
                .save(customerCaptor.capture());

        Customer savedCustomer = customerCaptor.getValue();

        assertEquals(
                "Rahul Sharma",
                savedCustomer.getCustomerName()
        );

        assertEquals(
                "rahul@gmail.com",
                savedCustomer.getEmail()
        );

        assertEquals(
                "9876543210",
                savedCustomer.getMobileNumber()
        );

        assertEquals(
                "encoded-password",
                savedCustomer.getHashedPassword()
        );

        assertTrue(
                savedCustomer.getRoles().contains(role)
        );

        Account savedAccount = savedCustomer.getAccount();

        assertNotNull(savedAccount);

        assertEquals(
                "Mumbai",
                savedAccount.getBranch()
        );

        assertEquals(
                AccountType.SAVING,
                savedAccount.getAccountType()
        );

        assertEquals(
                BigDecimal.ZERO,
                savedAccount.getBalance()
        );

        assertTrue(
                savedAccount.getAccountNumber() >= 100_000_000_000L
                        && savedAccount.getAccountNumber() < 1_000_000_000_000L
        );

        verify(passwordEncoder)
                .encode("password123");

        verify(customerRepository)
                .save(any(Customer.class));
    }


    @Test
    void shouldThrowExceptionWhenCustomerAlreadyExists() {

        // Arrange

        AccountRequestDto accountRequestDto =
                new AccountRequestDto(
                        "Mumbai",
                        AccountType.SAVING
                );

        CustomerRequestDto requestDto =
                new CustomerRequestDto(
                        "Rahul Sharma",
                        "rahul@gmail.com",
                        "9876543210",
                        "password123",
                        accountRequestDto
                );

        Customer existingCustomer = new Customer();

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.of(existingCustomer));


        // Act & Assert

        assertThrows(
                CustomerAlreadyExistException.class,
                () -> accountService.createAccount(requestDto)
        );

        verify(customerRepository)
                .findByMobileNumber("9876543210");

        verify(customerRepository, never())
                .save(any(Customer.class));

        verifyNoInteractions(
                roleRepository,
                compromisedPasswordChecker,
                passwordEncoder
        );
    }


    @Test
    void shouldThrowExceptionWhenAccountInformationIsMissing() {

        // Arrange

        CustomerRequestDto requestDto =
                new CustomerRequestDto(
                        "Rahul Sharma",
                        "rahul@gmail.com",
                        "9876543210",
                        "password123",
                        null
                );

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(
                IllegalArgumentException.class,
                () -> accountService.createAccount(requestDto)
        );

        verify(customerRepository)
                .findByMobileNumber("9876543210");

        verify(customerRepository, never())
                .save(any(Customer.class));
    }


    @Test
    void shouldThrowExceptionWhenRoleDoesNotExist() {

        // Arrange

        AccountRequestDto accountRequestDto =
                new AccountRequestDto(
                        "Mumbai",
                        AccountType.SAVING
                );

        CustomerRequestDto requestDto =
                new CustomerRequestDto(
                        "Rahul Sharma",
                        "rahul@gmail.com",
                        "9876543210",
                        "password123",
                        accountRequestDto
                );

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());

        when(roleRepository.findByRoleName("ROLE_CUSTOMER"))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(
                RuntimeException.class,
                () -> accountService.createAccount(requestDto)
        );

        verify(roleRepository)
                .findByRoleName("ROLE_CUSTOMER");

        verify(customerRepository, never())
                .save(any(Customer.class));

        verifyNoInteractions(
                compromisedPasswordChecker,
                passwordEncoder
        );
    }


    @Test
    void shouldThrowExceptionWhenPasswordIsCompromised() {

        // Arrange

        AccountRequestDto accountRequestDto =
                new AccountRequestDto(
                        "Mumbai",
                        AccountType.SAVING
                );

        CustomerRequestDto requestDto =
                new CustomerRequestDto(
                        "Rahul Sharma",
                        "rahul@gmail.com",
                        "9876543210",
                        "password123",
                        accountRequestDto
                );

        Role role = new Role();
        role.setRoleName("ROLE_CUSTOMER");

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());

        when(roleRepository.findByRoleName("ROLE_CUSTOMER"))
                .thenReturn(Optional.of(role));

        when(compromisedPasswordChecker.check("password123"))
                .thenReturn(
                        new CompromisedPasswordDecision(true)
                );


        // Act & Assert

        assertThrows(
                CompromisedPasswordException.class,
                () -> accountService.createAccount(requestDto)
        );

        verify(compromisedPasswordChecker)
                .check("password123");

        verify(passwordEncoder, never())
                .encode(anyString());

        verify(customerRepository, never())
                .save(any(Customer.class));
    }


    @Test
    void shouldReturnCustomerSuccessfully() {

        // Arrange

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.of(customer));

        // Act

        CustomerResponseDto result =
                accountService.getAccount("9876543210");

        // Assert

        assertNotNull(result);

        assertEquals(
                "Rahul Sharma",
                result.getCustomerName()
        );

        assertEquals(
                "rahul@gmail.com",
                result.getEmail()
        );

        assertEquals(
                "9876543210",
                result.getMobileNumber()
        );

        verify(customerRepository)
                .findByMobileNumber("9876543210");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        // Arrange

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());

        // Act & Assert

        assertThrows(
                ResourceNotFoundException.class,
                () -> accountService.getAccount("9876543210")
        );

        verify(customerRepository)
                .findByMobileNumber("9876543210");
    }

    @Test
    void shouldUpdateCustomerSuccessfully() {

        // Arrange

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("old@gmail.com");
        customer.setMobileNumber("9876543210");

        CustomerUpdateRequestDto requestDto =
                new CustomerUpdateRequestDto(
                        "Rahul Verma",
                        "rahul@gmail.com",
                        "9876543211"
                );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        // Act

        accountService.updateAccount(1L, requestDto);

        // Assert

        assertEquals(
                "Rahul Verma",
                customer.getCustomerName()
        );

        assertEquals(
                "rahul@gmail.com",
                customer.getEmail()
        );

        assertEquals(
                "9876543211",
                customer.getMobileNumber()
        );

        verify(customerRepository)
                .findById(1L);

        verify(customerRepository, never())
                .save(any(Customer.class));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFoundForUpdate() {

        // Arrange

        CustomerUpdateRequestDto requestDto =
                new CustomerUpdateRequestDto(
                        "Rahul Verma",
                        "rahul@gmail.com",
                        "9876543211"
                );

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert

        assertThrows(
                ResourceNotFoundException.class,
                () -> accountService.updateAccount(1L, requestDto)
        );

        verify(customerRepository)
                .findById(1L);

        verify(customerRepository, never())
                .save(any(Customer.class));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() {

        // Arrange

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setMobileNumber("9876543210");

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.of(customer));

        // Act

        accountService.deleteAccount("9876543210");

        // Assert

        verify(customerRepository)
                .findByMobileNumber("9876543210");

        verify(customerRepository)
                .delete(customer);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFoundForDelete() {

        // Arrange

        when(customerRepository.findByMobileNumber("9876543210"))
                .thenReturn(Optional.empty());

        // Act & Assert

        assertThrows(
                ResourceNotFoundException.class,
                () -> accountService.deleteAccount("9876543210")
        );

        verify(customerRepository)
                .findByMobileNumber("9876543210");

        verify(customerRepository, never())
                .delete(any(Customer.class));
    }


}