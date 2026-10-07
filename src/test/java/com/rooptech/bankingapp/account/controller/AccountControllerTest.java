package com.rooptech.bankingapp.account.controller;

import com.rooptech.bankingapp.account.dto.CustomerRequestDto;
import com.rooptech.bankingapp.account.dto.CustomerResponseDto;
import com.rooptech.bankingapp.account.dto.CustomerUpdateRequestDto;
import com.rooptech.bankingapp.account.exception.CustomerAlreadyExistException;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.service.AccountService;
import com.rooptech.bankingapp.common.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AccountControllerTest {

    private MockMvc mockMvc;

    private AccountService accountService;


    @BeforeEach
    void setUp() {

        accountService = mock(AccountService.class);

        AccountController accountController =
                new AccountController(accountService);

        LocalValidatorFactoryBean validator =
                new LocalValidatorFactoryBean();

        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setValidator(validator)
                .setControllerAdvice(
                        new GlobalExceptionHandler()
                )
                .build();
    }


    // ============================================================
    // CREATE ACCOUNT - POST
    // ============================================================

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {

        // Arrange
        doNothing()
                .when(accountService)
                .createAccount(
                        any(CustomerRequestDto.class)
                );

        // Act + Assert
        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isCreated());

        // Verify service was called
        verify(accountService)
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnConflictWhenCustomerAlreadyExists()
            throws Exception {

        // Arrange
        doThrow(
                new CustomerAlreadyExistException(
                        "customer already exist with given mobile number"
                )
        )
                .when(accountService)
                .createAccount(
                        any(CustomerRequestDto.class)
                );

        // Act + Assert
        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isConflict());

        // Verify service was called
        verify(accountService)
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenMobileNumberIsInvalid()
            throws Exception {

        // Act + Assert
        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "12345",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        // Service should NOT be called
        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "invalid-email",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenCustomerNameIsBlank()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenPasswordIsTooShort()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "pass123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenAccountDetailsAreMissing()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123"
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenBranchIsInvalid()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "M",
                                        "accountType": "SAVING"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenAccountTypeIsMissing()
            throws Exception {

        mockMvc.perform(
                        post("/accounts/api")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma",
                                      "email": "rahul@gmail.com",
                                      "mobileNumber": "9876543210",
                                      "password": "password123",
                                      "accountRequestDto": {
                                        "branch": "Mumbai"
                                      }
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        verify(accountService, never())
                .createAccount(
                        any(CustomerRequestDto.class)
                );
    }


    // ============================================================
    // GET ACCOUNT - GET
    // ============================================================

    @Test
    void shouldGetCustomerSuccessfully()
            throws Exception {

        // Arrange
        CustomerResponseDto responseDto =
                new CustomerResponseDto();

        when(accountService.getAccount("9876543210"))
                .thenReturn(responseDto);

        // Act + Assert
        mockMvc.perform(
                        get("/accounts/api/9876543210")
                )
                .andExpect(status().isOk());

        // Verify
        verify(accountService)
                .getAccount("9876543210");
    }


    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist()
            throws Exception {

        // Arrange
        when(accountService.getAccount("9876543210"))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Customer",
                                "mobileNumber",
                                "9876543210"
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get("/accounts/api/9876543210")
                )
                .andExpect(status().isNotFound());

        // Verify
        verify(accountService)
                .getAccount("9876543210");
    }


    // ============================================================
    // UPDATE ACCOUNT - PUT
    // ============================================================

    @Test
    void shouldUpdateCustomerSuccessfully()
            throws Exception {

        // Arrange
        doNothing()
                .when(accountService)
                .updateAccount(
                        eq(1L),
                        any(CustomerUpdateRequestDto.class)
                );

        // Act + Assert
        mockMvc.perform(
                        put("/accounts/api/1")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma Updated",
                                      "email": "rahul.updated@gmail.com",
                                      "mobileNumber": "9876543210"
                                    }
                                    """)
                )
                .andExpect(status().isOk());

        // Verify
        verify(accountService)
                .updateAccount(
                        eq(1L),
                        any(CustomerUpdateRequestDto.class)
                );
    }


    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingCustomer()
            throws Exception {

        // Arrange
        doThrow(
                new ResourceNotFoundException(
                        "Customer",
                        "customerId",
                        "1"
                )
        )
                .when(accountService)
                .updateAccount(
                        eq(1L),
                        any(CustomerUpdateRequestDto.class)
                );

        // Act + Assert
        mockMvc.perform(
                        put("/accounts/api/1")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "Rahul Sharma Updated",
                                      "email": "rahul.updated@gmail.com",
                                      "mobileNumber": "9876543210"
                                    }
                                    """)
                )
                .andExpect(status().isNotFound());

        // Verify
        verify(accountService)
                .updateAccount(
                        eq(1L),
                        any(CustomerUpdateRequestDto.class)
                );
    }


    @Test
    void shouldReturnBadRequestWhenUpdatingCustomerWithInvalidData()
            throws Exception {

        // Act + Assert
        mockMvc.perform(
                        put("/accounts/api/1")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                    {
                                      "customerName": "",
                                      "email": "invalid-email",
                                      "mobileNumber": "12345"
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest());

        // Service should NOT be called
        verify(accountService, never())
                .updateAccount(
                        anyLong(),
                        any(CustomerUpdateRequestDto.class)
                );
    }


    // ============================================================
    // DELETE ACCOUNT - DELETE
    // ============================================================

    @Test
    void shouldDeleteCustomerSuccessfully()
            throws Exception {

        // Arrange
        doNothing()
                .when(accountService)
                .deleteAccount("9876543210");

        // Act + Assert
        mockMvc.perform(
                        delete("/accounts/api/9876543210")
                )
                .andExpect(status().isOk());

        // Verify
        verify(accountService)
                .deleteAccount("9876543210");
    }


    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingCustomer()
            throws Exception {

        // Arrange
        doThrow(
                new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        "9876543210"
                )
        )
                .when(accountService)
                .deleteAccount("9876543210");

        // Act + Assert
        mockMvc.perform(
                        delete("/accounts/api/9876543210")
                )
                .andExpect(status().isNotFound());

        // Verify
        verify(accountService)
                .deleteAccount("9876543210");
    }
}