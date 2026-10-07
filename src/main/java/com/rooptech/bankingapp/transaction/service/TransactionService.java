package com.rooptech.bankingapp.transaction.service;

import com.rooptech.bankingapp.transaction.constant.TransactionType;
import com.rooptech.bankingapp.transaction.dto.TransactionRequestDto;
import com.rooptech.bankingapp.transaction.dto.TransactionResponseDto;
import com.rooptech.bankingapp.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    void deposit(
            TransactionRequestDto transactionRequestDto,
            Long accountId
                 );
    void withdraw(
            TransactionRequestDto transactionRequestDto,
            Long accountId);
    Page<TransactionResponseDto> getAllTransaction(
            Long accountId,
              TransactionType transactionType,
            Pageable pageable);
}
