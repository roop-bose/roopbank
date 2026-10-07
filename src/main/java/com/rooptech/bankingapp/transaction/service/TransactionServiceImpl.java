package com.rooptech.bankingapp.transaction.service;
import com.rooptech.bankingapp.account.entity.Account;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.account.repository.AccountRepository;
import com.rooptech.bankingapp.notification.dto.TransactionEvent;
import com.rooptech.bankingapp.notification.producer.TransactionEventProducer;
import com.rooptech.bankingapp.transaction.constant.TransactionStatus;
import com.rooptech.bankingapp.transaction.constant.TransactionType;
import com.rooptech.bankingapp.transaction.dto.TransactionRequestDto;
import com.rooptech.bankingapp.transaction.dto.TransactionResponseDto;
import com.rooptech.bankingapp.transaction.entity.Transaction;
import com.rooptech.bankingapp.transaction.mapper.TransactionMapper;
import com.rooptech.bankingapp.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl  implements TransactionService{
    private  final AccountRepository accountRepository;
    private  final TransactionRepository transactionRepository;
    private final TransactionEventProducer transactionEventProducer;
    @Override
    public void deposit(TransactionRequestDto transactionRequestDto, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Account",
                        "accountId",
                        accountId.toString()
                )
        );
        account.setBalance(account.getBalance().add(transactionRequestDto.getAmount()));
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setBalanceAfter(account.getBalance());
        transaction.setAccount(account);
        //transactionRepository.save(transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        //kafka notification


        TransactionEvent event = TransactionEvent.builder()
                .transactionId(savedTransaction.getTransactionId())
                .accountId(accountId)
                .transactionType(TransactionType.DEPOSIT)
                .amount(transactionRequestDto.getAmount())
                .transactionTime(savedTransaction.getTransactionDate())
                .build();

        transactionEventProducer.sendTransactionEvent(event);
//        Transaction savedTransaction=  transactionRepository.save(transaction);
//        return TransactionMapper.transactionResponseDto(savedTransaction
//                ,new TransactionResponseDto());
    }

    @Override
    public void withdraw(TransactionRequestDto transactionRequestDto, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Account",
                        "accountId",
                        accountId.toString()
                )
        );

        if (account.getBalance().compareTo(transactionRequestDto.getAmount())<0){
            throw  new IllegalStateException(
                    "Balance is insufficient "
            );
        }

        account.setBalance(account.getBalance().subtract(transactionRequestDto.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setBalanceAfter(account.getBalance());
        transaction.setAccount(account);
        //transactionRepository.save(transaction);

        //kafka notification
        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionEvent event = TransactionEvent.builder()
                .transactionId(savedTransaction.getTransactionId())
                .accountId(accountId)
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(transactionRequestDto.getAmount())
                .transactionTime(savedTransaction.getTransactionDate())
                .build();

        transactionEventProducer.sendTransactionEvent(event);

    }

    @Override
    public Page<TransactionResponseDto> getAllTransaction(
            Long accountId,
            TransactionType transactionType,
            Pageable pageable) {
        accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account",
                                "accountId",
                                accountId.toString()
                        )
                );

        Page<Transaction> transactions;
        if (transactionType==null){
            transactions = transactionRepository.findByAccountAccountId(
                    accountId,pageable
            );
        }else {
            transactions =transactionRepository.findByAccountAccountIdAndTransactionType(
                    accountId,
                    transactionType,
                    pageable
            );
        }


        return transactions.map(
                transaction -> TransactionMapper.transactionResponseDto(
                        transaction,
                        new TransactionResponseDto()
                )
        );

    }
}
