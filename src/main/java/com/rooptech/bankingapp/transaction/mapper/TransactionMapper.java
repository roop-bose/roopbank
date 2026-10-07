package com.rooptech.bankingapp.transaction.mapper;
import com.rooptech.bankingapp.transaction.dto.TransactionResponseDto;
import com.rooptech.bankingapp.transaction.entity.Transaction;

public class TransactionMapper {

    public static TransactionResponseDto transactionResponseDto(
            Transaction transaction,
            TransactionResponseDto
            transactionResponseDto
    ){
        transactionResponseDto.setTransactionId(transaction.getTransactionId());
        transactionResponseDto.setAmount(transaction.getAmount());
        transactionResponseDto.setBalanceAfter(transaction.getBalanceAfter());
        transactionResponseDto.setTransactionDate(transaction.getTransactionDate());
        transactionResponseDto.setTransactionType(transaction.getTransactionType());
        transactionResponseDto.setTransactionStatus(transaction.getTransactionStatus());
        if (transaction.getAccount() !=null){
            transactionResponseDto.setAccountId(transaction.getAccount().getAccountId());
        }
        return transactionResponseDto;
    }

}

