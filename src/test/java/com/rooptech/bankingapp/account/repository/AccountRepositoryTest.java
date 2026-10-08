package com.rooptech.bankingapp.account.repository;

import com.rooptech.bankingapp.account.constant.AccountType;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.common.AuditorAwareImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;


import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@Import(AuditorAwareImpl.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldSaveAndFindAccount() {

        Account account = new Account();
        account.setBranch("Mumbai");
        account.setAccountType(AccountType.SAVING);
        account.setAccountNumber(1000000001L);
        account.setBalance(new BigDecimal("5000.00"));

        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount.getAccountId())
                .isNotNull();

        Optional<Account> result =
                accountRepository.findById(savedAccount.getAccountId());

        assertThat(result).isPresent();
        assertThat(result.get().getBranch())
                .isEqualTo("Mumbai");
        assertThat(result.get().getAccountType())
                .isEqualTo(AccountType.SAVING);
        assertThat(result.get().getAccountNumber())
                .isEqualTo(1000000001L);
        assertThat(result.get().getBalance())
                .isEqualByComparingTo("5000.00");
    }

    @Test
    void shouldReturnEmptyWhenAccountDoesNotExist() {

        Optional<Account> result =
                accountRepository.findById(999999L);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldUpdateAccount() {

        Account account = new Account();
        account.setBranch("Mumbai");
        account.setAccountType(AccountType.SAVING);
        account.setAccountNumber(1000000002L);
        account.setBalance(new BigDecimal("5000.00"));

        Account savedAccount = accountRepository.save(account);

        savedAccount.setBranch("Pune");
        savedAccount.setBalance(new BigDecimal("10000.00"));

        accountRepository.save(savedAccount);

        Account updatedAccount =
                accountRepository.findById(savedAccount.getAccountId())
                        .orElseThrow();

        assertThat(updatedAccount.getBranch())
                .isEqualTo("Pune");
        assertThat(updatedAccount.getBalance())
                .isEqualByComparingTo("10000.00");
    }

    @Test
    void shouldDeleteAccount() {

        Account account = new Account();
        account.setBranch("Mumbai");
        account.setAccountType(AccountType.CURRENT);
        account.setAccountNumber(1000000003L);
        account.setBalance(new BigDecimal("25000.00"));

        Account savedAccount = accountRepository.save(account);

        Long accountId = savedAccount.getAccountId();

        accountRepository.deleteById(accountId);

        Optional<Account> result =
                accountRepository.findById(accountId);

        assertThat(result).isEmpty();
    }
}