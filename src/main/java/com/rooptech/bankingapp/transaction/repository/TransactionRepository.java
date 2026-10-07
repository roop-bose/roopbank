package com.rooptech.bankingapp.transaction.repository;
import com.rooptech.bankingapp.transaction.constant.TransactionType;
import com.rooptech.bankingapp.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    //List<Transaction> findByAccountAccountIdOrderByTransactionDateDesc(Long accountId);

    Page<Transaction> findByAccountAccountId(Long accountId, Pageable pageable);

    Page<Transaction> findByAccountAccountIdAndTransactionType(
            Long accountId,
            TransactionType transactionType,
            Pageable pageable);
}
